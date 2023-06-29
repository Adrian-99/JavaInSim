package pl.adrian.testutil;

import java.io.Closeable;
import java.io.IOException;
import java.net.*;

public class LfsUdpMock implements Closeable {
    private final int port;
    private final DatagramSocket socket;

    public LfsUdpMock(int port) throws SocketException {
        this.port = port;
        socket = new DatagramSocket();
    }

    @Override
    public void close() {
        socket.close();
    }

    public void send(byte[] bytes) throws IOException {
        var datagramPacket = new DatagramPacket(bytes, bytes.length, InetAddress.getByName("localhost"), port);
        socket.send(datagramPacket);
    }
}
