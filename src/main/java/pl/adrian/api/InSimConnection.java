package pl.adrian.api;

import pl.adrian.internal.Constants;
import pl.adrian.api.packets.IsiPacket;
import pl.adrian.internal.packets.base.ReadablePacket;
import pl.adrian.internal.packets.base.SendablePacket;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.exceptions.PacketReadingException;
import pl.adrian.internal.packets.util.PacketReader;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class is responsible for connecting and communicating with LFS
 */
public class InSimConnection implements Closeable {
    private final Socket socket;
    private final OutputStream out;
    private final InputStream in;
    private final ExecutorService readingExecutor;

    private boolean isConnected = false;

    /**
     * Creates InSim connection
     * @param hostname Address of the host where LFS is running
     * @param port Port which has been open by LFS for InSim connection
     * @param initializationPacket Packet sent upon connecting to initialize InSim
     * @throws IOException if I/O error occurs when creating a connection
     */
    public InSimConnection(String hostname, int port, IsiPacket initializationPacket) throws IOException {
        socket = new Socket(hostname, port);
        out = socket.getOutputStream();
        in = socket.getInputStream();
        readingExecutor = Executors.newSingleThreadExecutor();
        readingExecutor.execute(this::readIncomingPackets);
        send(initializationPacket);
    }

    @Override
    public void close() throws IOException {
        isConnected = false;
        readingExecutor.shutdownNow();
        in.close();
        out.close();
        socket.close();
    }

    /**
     * @return Whether InSim connection is alive
     */
    public boolean isConnected() {
        return isConnected;
    }

    /**
     * Sends InSim packet to LFS
     * @param packet Packet to be sent
     * @throws IOException if I/O error occurs while sending packet
     */
    public void send(SendablePacket packet) throws IOException {
        var bytes = packet.getBytes();
        out.write(bytes);
    }

    private void readIncomingPackets() {
        try {
            byte[] headerBytes;
            while ((headerBytes = in.readNBytes(Constants.PACKET_HEADER_SIZE)).length == Constants.PACKET_HEADER_SIZE) {
                var packetReader = new PacketReader(headerBytes);
                if (shouldPacketBeRead(packetReader.getPacketType())) {
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

    private boolean shouldPacketBeRead(PacketType packetType) {
        return packetType == PacketType.VER;
    }

    private void handleReadPacket(ReadablePacket packet) {
        switch (packet.getType()) {
            case VER -> {
                if (!isConnected) {
                    isConnected = true;
                }
            }
        }
    }
}
