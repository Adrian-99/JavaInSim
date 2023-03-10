package pl.adrian.api;

import pl.adrian.api.packets.SmallPacket;
import pl.adrian.api.packets.TinyPacket;
import pl.adrian.api.packets.enums.SmallSubtype;
import pl.adrian.api.packets.enums.TinySubtype;
import pl.adrian.internal.packets.util.Constants;
import pl.adrian.api.packets.IsiPacket;
import pl.adrian.internal.packets.util.PacketRequest;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.InfoPacket;
import pl.adrian.internal.packets.base.RequestablePacket;
import pl.adrian.internal.packets.base.InstructionPacket;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.exceptions.PacketReadingException;
import pl.adrian.internal.packets.util.PacketReader;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * This class is responsible for connecting and communicating with LFS.
 */
public class InSimConnection implements Closeable {
    private final Socket socket;
    private final OutputStream out;
    private final InputStream in;
    private final Random random;
    private final ScheduledExecutorService threadsExecutor;
    @SuppressWarnings("rawtypes")
    private final Map<PacketType, Set<PacketListener>> registeredListeners;
    @SuppressWarnings("rawtypes")
    private final List<PacketRequest> packetRequests;

    /**
     * Time period (in milliseconds) after which inactive packet requests will be removed.
     */
    protected short packetRequestTimeoutMs;
    /**
     * Time interval (in milliseconds) in which checks for inactive packet requests will be made.
     */
    protected short clearPacketRequestsIntervalMs;

    private boolean isConnected = false;
    private ScheduledFuture<?> clearPacketRequestsThread;


    /**
     * Creates InSim connection and sends specified initialization packet.
     * @param hostname address of the host where LFS is running
     * @param port port which has been open by LFS for InSim connection
     * @param initializationPacket packet sent upon connecting to initialize InSim
     * @throws IOException if I/O error occurs when creating a connection
     */
    public InSimConnection(String hostname, int port, IsiPacket initializationPacket) throws IOException {
        socket = new Socket(hostname, port);
        out = socket.getOutputStream();
        in = socket.getInputStream();
        random = new Random();
        threadsExecutor = Executors.newScheduledThreadPool(2);
        registeredListeners = new EnumMap<>(PacketType.class);
        packetRequests = new ArrayList<>();
        packetRequestTimeoutMs = 10000;
        clearPacketRequestsIntervalMs = 2000;

        threadsExecutor.submit(this::readIncomingPackets);
        send(initializationPacket);
    }

    @Override
    public void close() throws IOException {
        if (isConnected && !socket.isClosed()) {
            var closePacket = new TinyPacket(TinySubtype.CLOSE);
            send(closePacket);
        }

        isConnected = false;
        threadsExecutor.shutdownNow();
        in.close();
        out.close();
        socket.close();
    }

    /**
     * @return whether InSim connection is alive
     */
    public boolean isConnected() {
        return isConnected && !socket.isClosed();
    }

    /**
     * Sends specified {@link InstructionPacket} to LFS.
     * @param packet packet to be sent
     * @throws IOException if I/O error occurs while sending packet
     */
    public void send(InstructionPacket packet) throws IOException {
        var bytes = packet.getBytes();
        out.write(bytes);
    }

    /**
     * Registers packet listener - a function that will be called each time the packet of chosen
     * type will be received from LFS. It is possible to register multiple listeners for single
     * packet type, however duplicate listeners will be ignored. It is possible to unregister packet
     * listeners later on - see {@link #stopListening(Class, PacketListener) stopListening} method.
     * @param packetClass class of the packet to listen for
     * @param packetListener function that will be called each time the packet of chosen type is
     *                       received from LFS
     * @param <T> type of the packet to listen for
     */
    public <T extends Packet & InfoPacket> void listen(Class<T> packetClass,
                                                       PacketListener<T> packetListener) {
        if (packetClass != null && packetListener != null) {
            var packetType = PacketType.fromPacketClass(packetClass);
            registeredListeners.computeIfAbsent(packetType, type -> new HashSet<>());
            registeredListeners.get(packetType).add(packetListener);
        }
    }

    /**
     * Unregisters specified packet listener, if it has been registered before using
     * {@link #listen(Class, PacketListener) listen} method.
     * @param packetClass class of the packet that was listened for
     * @param packetListener function that was called each time the packet of chosen type was received from LFS
     * @param <T> type of the packet that was listened for
     */
    public <T extends Packet & InfoPacket> void stopListening(Class<T> packetClass,
                                                              PacketListener<T> packetListener) {
        if (packetClass != null && packetListener != null) {
            var packetType = PacketType.fromPacketClass(packetClass);
            if (registeredListeners.containsKey(packetType) &&
                    registeredListeners.get(packetType).contains(packetListener)) {
                if (registeredListeners.get(packetType).size() == 1) {
                    registeredListeners.remove(packetType);
                } else {
                    registeredListeners.get(packetType).remove(packetListener);
                }
            }
        }
    }

    /**
     * Requests packet(s) of specified type from LFS. Calling this method causes sending appropriate
     * {@link TinyPacket} to LFS that is a request for specified packet(s). The {@link TinyPacket}
     * contains randomly generated reqI value from range 1-255. When requested packet(s) is/are received,
     * specified callback function will be called (separately for each packet, if multiple).
     * @param packetClass class of the packet that is requested
     * @param callback function that will be called when requested packet is received
     * @param <T> type of the packet that is requested
     * @throws IOException if I/O error occurs when sending {@link TinyPacket}
     */
    public <T extends Packet & RequestablePacket> void request(Class<T> packetClass,
                                                               PacketListener<T> callback) throws IOException {
        var packetType = PacketType.fromPacketClass(packetClass);
        var tinySubtype = TinySubtype.fromRequestablePacketClass(packetClass);
        handleRequest(packetType, tinySubtype, callback);
    }

    /**
     * Requests specific {@link SmallPacket} from LFS. It is only possible to request {@link SmallPacket} of
     * {@link SmallSubtype#ALC ALC} subtype. Calling this method causes sending appropriate {@link TinyPacket}
     * to LFS that is a request for specified packet. The {@link TinyPacket} contains randomly generated reqI
     * value from range 1-255. When requested packet is received, specified callback function will be called.
     * @param tinySubtype {@link TinySubtype#ALC}, other values will be ignored
     * @param callback function that will be called when requested packet is received
     * @throws IOException if I/O error occurs when sending {@link TinyPacket}
     */
    public void request(TinySubtype tinySubtype, PacketListener<SmallPacket> callback) throws IOException {
        if (tinySubtype.equals(TinySubtype.ALC)) {
            handleRequest(PacketType.SMALL, tinySubtype, callback);
        }
    }

    private <T extends Packet & InfoPacket> void handleRequest(PacketType packetType,
                                                               TinySubtype tinySubtype,
                                                               PacketListener<T> callback) throws IOException {
        var allowedReqIs = IntStream.range(1, 256).filter(
                reqI -> packetRequests.stream().noneMatch(
                        request -> request.getReqI() == reqI && request.getPacketType().equals(packetType)
                )
        ).toArray();
        var reqIIndex = random.nextInt(0, allowedReqIs.length);
        var reqI = (short) allowedReqIs[reqIIndex];

        packetRequests.add(new PacketRequest<>(packetType, tinySubtype.isMultiPacketResponse(), reqI, callback));

        var tinyPacket = new TinyPacket(tinySubtype, reqI);
        send(tinyPacket);

        if (clearPacketRequestsThread == null || clearPacketRequestsThread.isCancelled()) {
            clearPacketRequestsThread = threadsExecutor.scheduleAtFixedRate(
                    this::clearTimedOutPacketRequests,
                    packetRequestTimeoutMs,
                    clearPacketRequestsIntervalMs,
                    TimeUnit.MILLISECONDS
            );
        }
    }

    private void readIncomingPackets() {
        try {
            byte[] headerBytes;
            while ((headerBytes = in.readNBytes(Constants.PACKET_HEADER_SIZE)).length == Constants.PACKET_HEADER_SIZE) {
                var packetReader = new PacketReader(headerBytes);
                if (shouldPacketBeRead(packetReader.getPacketType(), packetReader.getPacketReqI())) {
                    var dataBytes = in.readNBytes(packetReader.getDataBytesCount());
                    var packet = packetReader.read(dataBytes);
                    handleReadPacket(packet);
                } else {
                    in.skipNBytes(packetReader.getDataBytesCount());
                }
            }
            isConnected = false;
        } catch (IOException exception) {
            if (exception.getMessage().equals("Socket closed")) {
                isConnected = false;
            } else {
                throw new PacketReadingException("Error occurred while reading packet bytes", exception);
            }
        }
    }

    private boolean shouldPacketBeRead(PacketType packetType, short reqI) {
        return packetType == PacketType.VER ||
                packetType == PacketType.TINY ||
                registeredListeners.containsKey(packetType) ||
                packetRequests.stream().anyMatch(request ->
                        request.getPacketType().equals(packetType) && request.getReqI() == reqI
                );
    }

    private void handleReadPacket(InfoPacket packet) throws IOException {
        handleBasicReadPacket(packet);
        handleReadPacketForPacketListeners(packet);
        handleReadPacketForPacketRequests(packet);
    }

    private void handleBasicReadPacket(InfoPacket packet) throws IOException {
        if (packet.getType().equals(PacketType.VER)) {
            isConnected = true;
        } else if (packet.getType().equals(PacketType.TINY)) {
            var tinyPacket = (TinyPacket) packet;
            if (tinyPacket.getReqI() == 0 && tinyPacket.getSubT().equals(TinySubtype.NONE)) {
                send(tinyPacket);
                isConnected = true;
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void handleReadPacketForPacketListeners(InfoPacket packet) {
        if (registeredListeners.containsKey(packet.getType())) {
            threadsExecutor.submit(() -> {
                for (var listener : registeredListeners.get(packet.getType())) {
                    listener.onPacketReceived(this, packet);
                }
            });
        }
    }

    @SuppressWarnings("unchecked")
    private void handleReadPacketForPacketRequests(InfoPacket packet) {
        if (!packetRequests.isEmpty()) {
            for (var packetRequest : packetRequests) {
                if (packetRequest.getPacketType().equals(packet.getType()) &&
                        packetRequest.getReqI() == packet.getReqI()) {
                    threadsExecutor.submit(() ->
                            packetRequest.getCallback().onPacketReceived(this, packet)
                    );
                    if (!packetRequest.isExpectMultiPacketResponse()) {
                        packetRequests.remove(packetRequest);
                        tryToStopClearPacketRequestsThread();
                    } else {
                        packetRequest.setLastUpdateNow();
                    }
                    break;
                }
            }
        }
    }

    private void clearTimedOutPacketRequests() {
        packetRequests.removeIf(
                packetRequest -> packetRequest.getLastUpdateAt()
                        .until(LocalDateTime.now(), ChronoUnit.MILLIS) >
                        packetRequestTimeoutMs
        );
        tryToStopClearPacketRequestsThread();
    }

    private void tryToStopClearPacketRequestsThread() {
        if (packetRequests.isEmpty() && clearPacketRequestsThread != null) {
            clearPacketRequestsThread.cancel(false);
        }
    }
}
