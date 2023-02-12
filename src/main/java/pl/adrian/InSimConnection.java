package pl.adrian;

import pl.adrian.packets.IS_INI;
import pl.adrian.packets.base.ReadablePacket;
import pl.adrian.packets.base.SendablePacket;
import pl.adrian.packets.enums.PacketType;
import pl.adrian.packets.exceptions.PacketReadingException;
import pl.adrian.packets.util.PacketReader;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InSimConnection implements Closeable {
    private final Socket socket;
    private final OutputStream out;
    private final InputStream in;
    private final ExecutorService readingExecutor;

    public InSimConnection(String hostname, int port, IS_INI initializationPacket) throws IOException {
        socket = new Socket(hostname, port);
        out = socket.getOutputStream();
        in = socket.getInputStream();
        readingExecutor = Executors.newSingleThreadExecutor();
        readingExecutor.execute(this::readIncomingPackets);
        send(initializationPacket);
    }

    public void send(SendablePacket packet) throws IOException {
        var bytes = packet.getBytes();
        out.write(bytes);
    }

    @Override
    public void close() throws IOException {
        readingExecutor.shutdownNow();
        in.close();
        out.close();
        socket.close();
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
        } catch (IOException exception) {
            throw new PacketReadingException("Error occurred while reading packet bytes", exception);
        }
    }

    private boolean shouldPacketBeRead(PacketType packetType) {
        return packetType == PacketType.ISP_VER;
    }

    private void handleReadPacket(ReadablePacket packet) {
        packet.getSize();
    }
}
