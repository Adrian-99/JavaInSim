package pl.adrian.api;

import pl.adrian.api.packets.TinyPacket;
import pl.adrian.api.packets.enums.TinySubtype;
import pl.adrian.internal.Constants;
import pl.adrian.api.packets.IsiPacket;
import pl.adrian.internal.PacketRequest;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.ReadablePacket;
import pl.adrian.internal.packets.base.RequestablePacket;
import pl.adrian.internal.packets.base.SendablePacket;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.exceptions.PacketReadingException;
import pl.adrian.internal.packets.util.PacketReader;

import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class is responsible for connecting and communicating with LFS.
 */
public class InSimConnection implements Closeable {
    private final Socket socket;
    private final OutputStream out;
    private final InputStream in;
    private final Random random;
    private final ExecutorService threadsExecutor;
    @SuppressWarnings("rawtypes")
    private final Map<PacketType, Set<PacketListener>> registeredListeners;
    @SuppressWarnings("rawtypes")
    private final List<PacketRequest> packetRequests;

    private boolean isConnected = false;

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
        threadsExecutor = Executors.newFixedThreadPool(2);
        registeredListeners = new EnumMap<>(PacketType.class);
        packetRequests = new ArrayList<>();

        threadsExecutor.submit(this::readIncomingPackets);
        send(initializationPacket);
    }

    @Override
    public void close() throws IOException {
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
     * Sends specified {@link SendablePacket} to LFS.
     * @param packet packet to be sent
     * @throws IOException if I/O error occurs while sending packet
     */
    public void send(SendablePacket packet) throws IOException {
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
    public <T extends Packet & ReadablePacket> void listen(Class<T> packetClass,
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
    public <T extends Packet & ReadablePacket> void stopListening(Class<T> packetClass,
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
     * Requests packet of specified type from LFS. Calling this method causes sending appropriate
     * {@link TinyPacket} to LFS that is a request for specified packet. The {@link TinyPacket}
     * contains randomly generated reqI value from range 1-255. If requested packet is received,
     * specified callback function will be called.
     * @param packetClass class of the packet that is requested
     * @param callback function that will be called when requested packet is received
     * @param <T> type of the packet that is requested
     * @throws IOException if I/O error occurs when sending tiny packet
     */
    public <T extends Packet & RequestablePacket> void request(Class<T> packetClass,
                                                               PacketListener<T> callback)
            throws IOException {
        var packetType = PacketType.fromPacketClass(packetClass);
        var reqI = (short) random.nextInt(1, 256);
        var tinyPacket = new TinyPacket(reqI, TinySubtype.fromRequestablePacketClass(packetClass));
        packetRequests.add(new PacketRequest<>(packetType, reqI, callback));
        send(tinyPacket);
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
                        request.packetType().equals(packetType) && request.reqI() == reqI
                );
    }

    private void handleReadPacket(ReadablePacket packet) throws IOException {
        handleBasicReadPacket(packet);
        handleReadPacketForPacketListeners(packet);
        handleReadPacketForPacketRequests(packet);
    }

    private void handleBasicReadPacket(ReadablePacket packet) throws IOException {
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
    private void handleReadPacketForPacketListeners(ReadablePacket packet) {
        if (registeredListeners.containsKey(packet.getType())) {
            threadsExecutor.submit(() -> {
                for (var listener : registeredListeners.get(packet.getType())) {
                    listener.onPacketReceived(this, packet);
                }
            });
        }
    }

    @SuppressWarnings("unchecked")
    private void handleReadPacketForPacketRequests(ReadablePacket packet) {
        if (!packetRequests.isEmpty()) {
            for (var packetRequest : packetRequests) {
                if (packetRequest.packetType().equals(packet.getType()) &&
                        packetRequest.reqI() == packet.getReqI()) {
                    threadsExecutor.submit(() ->
                            packetRequest.callback().onPacketReceived(this, packet)
                    );
                    packetRequests.remove(packetRequest);
                    break;
                }
            }
        }
    }
}
