package pl.adrian.api.outgauge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.adrian.internal.common.util.LoggerUtils;
import pl.adrian.internal.common.util.PacketDataBytes;

import java.io.Closeable;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * This class is responsible for OutGauge connection to LFS.
 */
public class OutGaugeConnection implements Closeable {
    private final Logger logger = LoggerFactory.getLogger(OutGaugeConnection.class);
    private final DatagramSocket serverSocket;
    private final Set<Consumer<OutGaugePacket>> registeredListeners;
    private final ExecutorService listenerExecutor;

    /**
     * Creates OutGauge connection.
     * @param port IP port - should match value from cfg.txt
     * @throws SocketException if error occurs when creating a connection
     */
    public OutGaugeConnection(int port) throws SocketException {
        logger.debug("Creating OutGauge connection");
        serverSocket = new DatagramSocket(port);
        registeredListeners = new HashSet<>();
        listenerExecutor = Executors.newSingleThreadExecutor();
        listenerExecutor.submit(this::readIncomingPackets);
    }

    @Override
    public void close() {
        logger.debug("Closing OutGauge connection");
        listenerExecutor.shutdownNow();
        serverSocket.close();
    }

    /**
     * @return whether OutGauge connection is alive
     */
    public boolean isConnected() {
        return !serverSocket.isClosed();
    }

    /**
     * Registers packet listener - a function that will be called each time the OutGauge packet
     * will be received from LFS. It is possible to register multiple listeners, however duplicate
     * listeners will be ignored. It is possible to unregister packet listeners later on - see
     * {@link #stopListening} method.
     * @param packetListener function that will be called each time the OutGauge packet is received from LFS
     */
    public void listen(Consumer<OutGaugePacket> packetListener) {
        if (packetListener != null) {
            logger.debug("Registering OutGauge listener");
            registeredListeners.add(packetListener);
        }
    }

    /**
     * Unregisters specified packet listener, if it has been registered before using {@link #listen} method.
     * @param packetListener function that was called each time the OutGauge packet was received from LFS
     */
    public void stopListening(Consumer<OutGaugePacket> packetListener) {
        if (packetListener != null) {
            logger.debug("Unregistering OutGauge listener");
            registeredListeners.remove(packetListener);
        }
    }

    private void readIncomingPackets() {
        logger.debug("Started packet reading thread");
        try {
            var buffer = new byte[OutGaugePacket.SIZE];
            var datagramPacket = new DatagramPacket(buffer, buffer.length);
            while (!serverSocket.isClosed()) {
                serverSocket.receive(datagramPacket);
                if (!registeredListeners.isEmpty()) {
                    var packetDataBytes = new PacketDataBytes(buffer);
                    var packet = new OutGaugePacket(packetDataBytes);
                    handleReadPacket(packet);
                }
            }
        } catch (IOException exception) {
            logger.error("Error occurred while reading OutGauge packet: {}", exception.getMessage());
            LoggerUtils.logStacktrace(logger, "reading OutGauge packet", exception);
        }
        logger.debug("Stopping packet reading thread");
    }

    private void handleReadPacket(OutGaugePacket packet) {
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
