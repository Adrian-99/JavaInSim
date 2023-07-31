package pl.adrian.api.insim;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.adrian.api.outgauge.OutGaugeConnection;
import pl.adrian.api.outsim.OutSimConnection;
import pl.adrian.api.outsim.flags.OutSimOpts;
import pl.adrian.api.insim.packets.*;
import pl.adrian.api.insim.packets.enums.*;
import pl.adrian.api.common.flags.Flags;
import pl.adrian.internal.insim.packets.requests.PacketRequest;
import pl.adrian.internal.insim.packets.requests.PacketRequests;
import pl.adrian.internal.insim.packets.util.Constants;
import pl.adrian.internal.common.util.LoggerUtils;
import pl.adrian.internal.insim.packets.base.InfoPacket;
import pl.adrian.internal.insim.packets.base.InstructionPacket;
import pl.adrian.internal.insim.packets.util.PacketReader;

import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.*;

/**
 * This class is responsible for InSim connection to LFS.
 */
public class InSimConnection implements Closeable {
    private final Logger logger = LoggerFactory.getLogger(InSimConnection.class);
    private final int udpPort;
    @SuppressWarnings("rawtypes")
    private final Map<PacketType, Set<PacketListener>> registeredListeners;
    private final PacketRequests packetRequests;

    private Socket socket;
    private OutputStream out;
    private InputStream in;
    private ExecutorService executorService;
    private boolean isConnected = false;

    /**
     * Creates InSim connection and sends specified initialization packet.
     * @param hostname address of the host where LFS is running
     * @param port port which has been open by LFS for InSim connection
     * @param initializationPacket packet sent upon connecting to initialize InSim
     * @throws IOException if I/O error occurs when creating a connection
     */
    public InSimConnection(String hostname, int port, IsiPacket initializationPacket) throws IOException {
        this(hostname, port, initializationPacket, 2000);
    }

    /**
     * Creates InSim connection and sends specified initialization packet.
     * @param hostname address of the host where LFS is running
     * @param port port which has been open by LFS for InSim connection
     * @param initializationPacket packet sent upon connecting to initialize InSim
     * @param requestsCleanUpInterval interval (milliseconds) at which timed out packet requests should be cleaned up
     * @throws IOException if I/O error occurs when creating a connection
     */
    protected InSimConnection(String hostname,
                              int port,
                              IsiPacket initializationPacket,
                              long requestsCleanUpInterval) throws IOException {
        logger.debug("Creating InSim connection");
        udpPort = initializationPacket.getUdpPort();
        registeredListeners = new EnumMap<>(PacketType.class);
        packetRequests = new PacketRequests(requestsCleanUpInterval);
        connect(hostname, port, initializationPacket);
    }

    @Override
    public void close() throws IOException {
        logger.debug("Closing InSim connection");
        if (isConnected && !socket.isClosed()) {
            var closePacket = new TinyPacket(0, TinySubtype.CLOSE);
            send(closePacket);
        }
        isConnected = false;
        executorService.shutdownNow();
        packetRequests.close();
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
     * listeners later on - see {@link #stopListening} method.
     * @param packetClass class of the packet to listen for
     * @param packetListener function that will be called each time the packet of chosen type is
     *                       received from LFS
     * @param <T> type of the packet to listen for
     */
    public <T extends InfoPacket> void listen(Class<T> packetClass,
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
     * {@link #listen} method.
     * @param packetClass class of the packet that was listened for
     * @param packetListener function that was called each time the packet of chosen type was received from LFS
     * @param <T> type of the packet that was listened for
     */
    public <T extends InfoPacket> void stopListening(Class<T> packetClass,
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
     * Requests packet(s) described by specified packet request from LFS. Calling this method
     * sends appropriate request packet and adds packet request to pending packet requests list.
     * @param packetRequest packet request describing requested packet
     * @throws IOException if I/O error occurs when sending request packet
     */
    public void request(PacketRequest packetRequest) throws IOException {
        packetRequests.add(packetRequest);
        send(packetRequest.getRequestPacket());
    }

    /**
     * Initializes OutSim from InSim. If OutSim has not been set up in cfg.txt, this method makes LFS send UDP packets
     * if in game, using the OutSim system. The OutSim packets will be sent to the UDP port specified in the
     * {@link IsiPacket}. This method sends appropriate {@link SmallSubtype#SSP SSP} {@link SmallPacket}
     * to LFS and creates {@link OutSimConnection}. To cancel sending OutSim packets by LFS, {@link #stopOutSim}
     * method should be used.
     * @param interval time between updates - must be greater than 0
     * @param opts OutSim options - should match value from cfg.txt
     * @return active OutSim connection
     * @throws IOException if I/O error occurs while sending {@link SmallPacket} or creating {@link OutSimConnection}
     */
    public OutSimConnection initializeOutSim(long interval, int opts) throws IOException {
        return initializeOutSim(interval, new Flags<>(OutSimOpts.class, opts));
    }

    /**
     * Initializes OutSim from InSim. If OutSim has not been set up in cfg.txt, this method makes LFS send UDP packets
     * if in game, using the OutSim system. The OutSim packets will be sent to the UDP port specified in the
     * {@link IsiPacket}. This method sends appropriate {@link SmallSubtype#SSP SSP} {@link SmallPacket}
     * to LFS and creates {@link OutSimConnection}. To cancel sending OutSim packets by LFS, {@link #stopOutSim}
     * method should be used.
     * @param interval time between updates - must be greater than 0
     * @param opts OutSim options - should match value from cfg.txt
     * @return active OutSim connection
     * @throws IOException if I/O error occurs while sending {@link SmallPacket} or creating {@link OutSimConnection}
     */
    public OutSimConnection initializeOutSim(long interval, Flags<OutSimOpts> opts) throws IOException {
        send(new SmallPacket(SmallSubtype.SSP, interval));
        return new OutSimConnection(udpPort, opts);
    }

    /**
     * Cancels sending OutSim packets by LFS that have been previously requested using {@link #initializeOutSim}
     * method. This method sends appropriate {@link SmallSubtype#SSP SSP} {@link SmallPacket} to LFS.
     * @throws IOException if I/O error occurs while sending {@link SmallPacket}
     */
    public void stopOutSim() throws IOException {
        send(new SmallPacket(SmallSubtype.SSP, 0));
    }

    /**
     * Initializes OutGauge from InSim. If OutGauge has not been set up in cfg.txt, this method makes LFS send UDP packets
     * if in game, using the OutGauge system. The OutGauge packets will be sent to the UDP port specified in the
     * {@link IsiPacket}. This method sends appropriate {@link SmallSubtype#SSG SSG} {@link SmallPacket}
     * to LFS and creates {@link OutGaugeConnection}. To cancel sending OutGauge packets by LFS, {@link #stopOutGauge}
     * method should be used.
     * @param interval time between updates - must be greater than 0
     * @return active OutGauge connection
     * @throws IOException if I/O error occurs while sending {@link SmallPacket} or creating {@link OutGaugeConnection}
     */
    public OutGaugeConnection initializeOutGauge(long interval) throws IOException {
        send(new SmallPacket(SmallSubtype.SSG, interval));
        return new OutGaugeConnection(udpPort);
    }

    /**
     * Cancels sending OutGauge packets by LFS that have been previously requested using {@link #initializeOutGauge}
     * method. This method sends appropriate {@link SmallSubtype#SSG SSG} {@link SmallPacket} to LFS.
     * @throws IOException if I/O error occurs while sending {@link SmallPacket}
     */
    public void stopOutGauge() throws IOException {
        send(new SmallPacket(SmallSubtype.SSG, 0));
    }

    /**
     * Creates InSim connection and sends specified initialization packet.
     * @param hostname address of the host where LFS is running
     * @param port port which has been open by LFS for InSim connection
     * @param initializationPacket packet sent upon connecting to initialize InSim
     * @throws IOException if I/O error occurs when creating a connection
     */
    protected void connect(String hostname, int port, IsiPacket initializationPacket) throws IOException {
        socket = new Socket(hostname, port);
        out = socket.getOutputStream();
        in = socket.getInputStream();
        executorService = Executors.newSingleThreadExecutor();
        executorService.submit(this::readIncomingPackets);
        send(initializationPacket);
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
            LoggerUtils.logStacktrace(logger, "reading packet header", exception);
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
            LoggerUtils.logStacktrace(logger, "reading packet", exception);
        }
    }

    private boolean shouldPacketBeRead(PacketType packetType, short reqI) {
        return packetType == PacketType.VER ||
                packetType == PacketType.TINY ||
                registeredListeners.containsKey(packetType) ||
                packetRequests.anyMatch(packetType, reqI);
    }

    private void handleReadPacket(InfoPacket packet) throws IOException {
        handleBasicReadPacket(packet);
        handleReadPacketForPacketListeners(packet);
        packetRequests.handle(this, packet);
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
            for (var listener : registeredListeners.get(packet.getType())) {
                try {
                    listener.onPacketReceived(this, packet);
                } catch (Exception exception) {
                    logger.error("Error occurred in packet listener callback: {}", exception.getMessage());
                    LoggerUtils.logStacktrace(logger, "listener callback", exception);
                }
            }
        }
    }
}
