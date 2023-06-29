package pl.adrian.api.outsim;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.adrian.api.outsim.flags.OutSimOpts;
import pl.adrian.api.outsim.structures.*;
import pl.adrian.api.common.flags.Flags;
import pl.adrian.internal.common.util.LoggerUtils;
import pl.adrian.internal.common.util.PacketDataBytes;

import java.io.Closeable;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * This class is responsible for OutSim connection to LFS.
 */
public class OutSimConnection implements Closeable {
    private final Logger logger = LoggerFactory.getLogger(OutSimConnection.class);
    private final DatagramSocket serverSocket;
    private final Flags<OutSimOpts> opts;
    private final boolean outSimPack2Requested;
    private final short packetSize;
    private final Set<Consumer<OutSimPacket2>> registeredListeners;
    private final ExecutorService listenerExecutor;

    /**
     * Creates OutSim connection.
     * @param port IP port - should match value from cfg.txt
     * @param opts OutSim options - should match value from cfg.txt
     * @throws IOException if I/O error occurs when creating a connection
     */
    public OutSimConnection(int port, int opts) throws IOException {
        this(port, new Flags<>(OutSimOpts.class, opts));
    }

    /**
     * Creates OutSim connection.
     * @param port IP port - should match value from cfg.txt
     * @param opts OutSim options - should match value from cfg.txt
     * @throws IOException if I/O error occurs when creating a connection
     */
    public OutSimConnection(int port, Flags<OutSimOpts> opts) throws IOException {
        logger.debug("Creating OutSim connection");
        serverSocket = new DatagramSocket(port);
        this.opts = opts;
        if (opts.getUnsignedValue() > 0) {
            outSimPack2Requested = true;
            packetSize = calculateOutSimPack2Size();
        } else {
            outSimPack2Requested = false;
            packetSize = OutSimTime.SIZE + OutSimMain.SIZE + OutSimId.SIZE;
        }
        registeredListeners = new HashSet<>();
        listenerExecutor = Executors.newSingleThreadExecutor();
        listenerExecutor.submit(this::readIncomingPackets);
    }

    @Override
    public void close() {
        logger.debug("Closing OutSim connection");
        listenerExecutor.shutdownNow();
        serverSocket.close();
    }

    /**
     * @return whether OutSim connection is alive
     */
    public boolean isConnected() {
        return !serverSocket.isClosed();
    }

    /**
     * Registers packet listener - a function that will be called each time the OutSim packet
     * will be received from LFS. It is possible to register multiple listeners, however duplicate
     * listeners will be ignored. It is possible to unregister packet listeners later on - see
     * {@link #stopListening} method.
     * @param packetListener function that will be called each time the OutSim packet is received from LFS
     */
    public void listen(Consumer<OutSimPacket2> packetListener) {
        if (packetListener != null) {
            logger.debug("Registering OutSim listener");
            registeredListeners.add(packetListener);
        }
    }

    /**
     * Unregisters specified packet listener, if it has been registered before using {@link #listen} method.
     * @param packetListener function that was called each time the OutSim packet was received from LFS
     */
    public void stopListening(Consumer<OutSimPacket2> packetListener) {
        if (packetListener != null) {
            logger.debug("Unregistering OutSim listener");
            registeredListeners.remove(packetListener);
        }
    }

    private short calculateOutSimPack2Size() {
        var size = 0;
        if (opts.hasFlag(OutSimOpts.HEADER)) {
            size += OutSimHeader.SIZE;
        }
        if (opts.hasFlag(OutSimOpts.ID)) {
            size += OutSimId.SIZE;
        }
        if (opts.hasFlag(OutSimOpts.TIME)) {
            size += OutSimTime.SIZE;
        }
        if (opts.hasFlag(OutSimOpts.MAIN)) {
            size += OutSimMain.SIZE;
        }
        if (opts.hasFlag(OutSimOpts.INPUTS)) {
            size += OutSimInputs.SIZE;
        }
        if (opts.hasFlag(OutSimOpts.DRIVE)) {
            size += OutSimDrive.SIZE;
        }
        if (opts.hasFlag(OutSimOpts.DISTANCE)) {
            size += OutSimDistance.SIZE;
        }
        if (opts.hasFlag(OutSimOpts.WHEELS)) {
            size += 4 * OutSimWheel.SIZE;
        }
        if (opts.hasFlag(OutSimOpts.EXTRA_1)) {
            size += OutSimExtra1.SIZE;
        }
        return (short) size;
    }

    private void readIncomingPackets() {
        logger.debug("Started packet reading thread");
        try {
            var buffer = new byte[packetSize];
            var datagramPacket = new DatagramPacket(buffer, buffer.length);
            while (!serverSocket.isClosed()) {
                serverSocket.receive(datagramPacket);
                if (!registeredListeners.isEmpty()) {
                    var packetDataBytes = new PacketDataBytes(buffer);
                    var packet = outSimPack2Requested ?
                            new OutSimPacket2(opts, packetDataBytes) :
                            new OutSimPacket2(packetDataBytes);
                    handleReadPacket(packet);
                }
            }
            logger.error("Lost connection to LFS");
        } catch (IOException exception) {
            logger.error("Error occurred while reading OutSim packet: {}", exception.getMessage());
            LoggerUtils.logStacktrace(logger, "reading OutSim packet", exception);
        }
        logger.debug("Stopping packet reading thread");
    }

    private void handleReadPacket(OutSimPacket2 packet) {
        for (var packetListener : registeredListeners) {
            try {
                packetListener.accept(packet);
            } catch (Exception exception) {
                logger.error("Error occurred in packet listener callback: {}", exception.getMessage());
                LoggerUtils.logStacktrace(logger, "listener callback", exception);
            }
        }
    }
}
