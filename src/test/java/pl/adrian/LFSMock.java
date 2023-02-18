package pl.adrian;

import pl.adrian.packets.enums.Product;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

public class LFSMock implements Closeable {
    private final ExecutorService listenExecutor;
    private final List<byte[]> receivedPacketBytes;
    private final Product product;
    private final String version;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private InputStream in;
    private OutputStream out;

    public LFSMock(int port, Product product, String version) {
        this.product = product;
        if (version.length() > 8) {
            version = version.substring(0, 8);
        }
        this.version = version;
        receivedPacketBytes = new ArrayList<>();

        listenExecutor = Executors.newSingleThreadExecutor();
        listenExecutor.execute(() -> listen(port));
    }

    @Override
    public void close() throws IOException {
        listenExecutor.shutdownNow();
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }

    public List<byte[]> awaitReceivedPackets(int packetsCount) {
        await().atMost(5, TimeUnit.SECONDS)
                .with().pollInterval(100, TimeUnit.MILLISECONDS)
                .until(() -> receivedPacketBytes.size() >= packetsCount);

        return receivedPacketBytes;
    }

    public void send(byte[] bytes) throws IOException {
        out.write(bytes);
    }

    private void sendVersionPacket(byte reqI) throws IOException {
        out.write(new byte[] { 4, 2, reqI, 0 });
        out.write(version.getBytes());
        for (var i = version.length(); i < 8; i++) {
            out.write(0);
        }
        out.write(product.toString().getBytes());
        for (var i = product.toString().length(); i < 6; i++) {
            out.write(0);
        }
        out.write(new byte[] { 9, 0 });
    }

    private void listen(int port) {
        try {
            serverSocket = new ServerSocket(port);
            clientSocket = serverSocket.accept();
            out = clientSocket.getOutputStream();
            in = clientSocket.getInputStream();
            int sizeByte;
            while ((sizeByte = in.read()) != -1) {
                var remainingPacketBytes = in.readNBytes(sizeByte * 4 - 1);

                var packetBytes = new byte[sizeByte * 4];
                if (sizeByte > 127) {
                    sizeByte = sizeByte - 256;
                }
                packetBytes[0] = (byte) sizeByte;
                System.arraycopy(remainingPacketBytes, 0, packetBytes, 1, packetBytes.length - 1);
                receivedPacketBytes.add(packetBytes);

                if (remainingPacketBytes[0] == 1 && remainingPacketBytes[1] != 0) {
                    sendVersionPacket(remainingPacketBytes[1]);
                }
            }
        } catch (Exception e) {
            if (!e.getMessage().equals("Socket closed")) {
                throw new RuntimeException(e);
            }
        }
    }
}
