package pl.adrian.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.adrian.api.packets.*;
import pl.adrian.api.packets.enums.*;
import pl.adrian.internal.packets.util.Constants;
import pl.adrian.internal.packets.util.PacketRequest;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.InfoPacket;
import pl.adrian.internal.packets.base.RequestablePacket;
import pl.adrian.internal.packets.base.InstructionPacket;
import pl.adrian.internal.packets.util.PacketReader;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * This class is responsible for connecting and communicating with LFS.
 */
public class InSimConnection implements Closeable {
    private final Logger logger = LoggerFactory.getLogger(InSimConnection.class);
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
        logger.debug("Creating InSim connection");
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
        logger.debug("Closing InSim connection");
        if (isConnected && !socket.isClosed()) {
            var closePacket = new TinyPacket(0, TinySubtype.CLOSE);
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
        logger.debug("Sending {} packet", packet.getType());
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
            logger.debug("Registering listener for {} packets", packetType);
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
                logger.debug("Unregistering listener for {} packets", packetType);
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
     * @param callback function that will be called when requested packet(s) is/are received
     * @param <T> type of the packet that is requested
     * @throws IOException if I/O error occurs when sending {@link TinyPacket}
     */
    public <T extends Packet & RequestablePacket> void request(Class<T> packetClass,
                                                               PacketListener<T> callback) throws IOException {
        var packetType = PacketType.fromPacketClass(packetClass);
        logger.debug("Requested {} packet(s)", packetType);
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
            logger.debug("Requested SMALL {} packet", tinySubtype);
            handleRequest(PacketType.SMALL, tinySubtype, callback);
        }
    }

    /**
     * Requests {@link PmoAction#TTC_SEL} {@link AxmPacket} from LFS for specified UCID. Calling this method
     * causes sending appropriate {@link TtcPacket} to LFS that is a request for {@link AxmPacket}. The
     * {@link TtcPacket} contains randomly generated reqI value from range 1-255. When requested packet(s) is/are
     * received, specified callback function will be called (separately for each packet, if multiple).
     * @param ucid unique connection id
     * @param callback function that will be called when requested packet(s) is/are received
     * @throws IOException if I/O error occurs when sending {@link TtcPacket}
     */
    public void request(int ucid, PacketListener<AxmPacket> callback) throws IOException {
        logger.debug("Requested AXM SEL packet");

        var reqI = getFreeReqI(PacketType.AXM);

        packetRequests.add(new PacketRequest<>(PacketType.AXM, true, reqI, callback));

        var ttcPacket = new TtcPacket(TtcSubtype.SEL, ucid, 0, 0, 0, reqI);
        send(ttcPacket);

        tryToScheduleClearPacketRequestsThread();
    }

    private <T extends Packet & InfoPacket> void handleRequest(PacketType packetType,
                                                               TinySubtype tinySubtype,
                                                               PacketListener<T> callback) throws IOException {
        var reqI = getFreeReqI(packetType);

        packetRequests.add(new PacketRequest<>(packetType, tinySubtype.isMultiPacketResponse(), reqI, callback));

        var tinyPacket = new TinyPacket(reqI, tinySubtype);
        send(tinyPacket);

        tryToScheduleClearPacketRequestsThread();
    }

    private short getFreeReqI(PacketType packetType) {
        var allowedReqIs = IntStream.range(1, 256).filter(
                reqI -> packetRequests.stream().noneMatch(
                        request -> request.getReqI() == reqI && request.getPacketType().equals(packetType)
                )
        ).toArray();
        var reqIIndex = random.nextInt(0, allowedReqIs.length);
        return (short) allowedReqIs[reqIIndex];
    }

    private void tryToScheduleClearPacketRequestsThread() {
        if (clearPacketRequestsThread == null || clearPacketRequestsThread.isCancelled()) {
            logger.debug("Scheduling clearing timed out packet requests thread");
            clearPacketRequestsThread = threadsExecutor.scheduleAtFixedRate(
                    this::clearTimedOutPacketRequests,
                    packetRequestTimeoutMs,
                    clearPacketRequestsIntervalMs,
                    TimeUnit.MILLISECONDS
            );
        }
    }

    private void readIncomingPackets() {
        logger.debug("Started packet reading thread");
        try {
            byte[] headerBytes;
            while ((headerBytes = in.readNBytes(Constants.PACKET_HEADER_SIZE)).length == Constants.PACKET_HEADER_SIZE) {
                onPacketReceived(headerBytes);
            }
            isConnected = false;
            logger.error("Lost connection to LFS");
        } catch (IOException exception) {
            isConnected = false;
            logger.error("Error occurred while reading packet header bytes: {}", exception.getMessage());
            logStackTrace("reading packet header", exception);
        }
        logger.debug("Stopping packet reading thread");
    }

    private void onPacketReceived(byte[] headerBytes) {
        try {
            var packetReader = new PacketReader(headerBytes);
            if (shouldPacketBeRead(packetReader.getPacketType(), packetReader.getPacketReqI())) {
                logger.atDebug().log("Received {} packet - reading", packetReader.getPacketType());
                var dataBytes = in.readNBytes(packetReader.getDataBytesCount());
                var packet = packetReader.read(dataBytes);
                handleReadPacket(packet);
            } else {
                logger.atDebug().log("Received {} packet - skipping", packetReader.getPacketType());
                in.skipNBytes(packetReader.getDataBytesCount());
            }
        } catch (Exception exception) {
            logger.error("Error occurred while reading packet: {}", exception.getMessage());
            logStackTrace("reading packet", exception);
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
                logger.debug("Received keep alive packet");
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
                    try {
                        listener.onPacketReceived(this, packet);
                    } catch (Exception exception) {
                        logger.error("Error occurred in packet listener callback: {}", exception.getMessage());
                        logStackTrace("listener callback", exception);
                    }
                }
            });
        }
    }

    @SuppressWarnings("unchecked")
    private void handleReadPacketForPacketRequests(InfoPacket packet) {
        for (var packetRequest : packetRequests) {
            if (packetRequest.getPacketType().equals(packet.getType()) &&
                    packetRequest.getReqI() == packet.getReqI()) {
                threadsExecutor.submit(() -> {
                    try {
                        packetRequest.getCallback().onPacketReceived(this, packet);
                    } catch (Exception exception) {
                        logger.error("Error occurred in packet request callback: {}", exception.getMessage());
                        logStackTrace("packet request callback", exception);
                    }
                });
                if (!packetRequest.isExpectMultiPacketResponse()) {
                    logger.debug("Removing {} packet request - packet has been received", packetRequest.getPacketType());
                    packetRequests.remove(packetRequest);
                    tryToStopClearPacketRequestsThread();
                } else {
                    packetRequest.setLastUpdateNow();
                }
                break;
            }
        }
    }

    private void clearTimedOutPacketRequests() {
        logger.debug("Trying to remove timed out packet requests");
        packetRequests.removeIf(
                packetRequest -> packetRequest.getLastUpdateAt()
                        .until(LocalDateTime.now(), ChronoUnit.MILLIS) >
                        packetRequestTimeoutMs
        );
        tryToStopClearPacketRequestsThread();
    }

    private void tryToStopClearPacketRequestsThread() {
        if (packetRequests.isEmpty() && clearPacketRequestsThread != null) {
            logger.debug("Stopping clearing timed out packet requests thread - no packet requests left");
            clearPacketRequestsThread.cancel(false);
        }
    }

    private void logStackTrace(String step, Exception exception) {
        if (logger.isDebugEnabled()) {
            var messageBuilder = new StringBuilder(exception.getClass().getSimpleName())
                    .append(" thrown in ")
                    .append(step)
                    .append(": ")
                    .append(exception.getMessage());
            for (var stackTraceElement : exception.getStackTrace()) {
                messageBuilder.append("\nat ")
                        .append(stackTraceElement.toString());
            }
            logger.debug(messageBuilder.toString());
        }
    }
}
