/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.testutil;

import com.github.adrian99.javainsim.api.insim.packets.enums.Product;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketBuilder;

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

public class LfsTcpMock implements Closeable {
    private final ExecutorService listenExecutor;
    private final List<byte[]> receivedPacketBytes;
    private final Product product;
    private final String version;
    private final ServerSocket serverSocket;
    private Socket clientSocket;
    private InputStream in;
    private OutputStream out;

    public LfsTcpMock(int port, Product product, String version) throws IOException {
        this.product = product;
        if (version.length() > 8) {
            version = version.substring(0, 8);
        }
        this.version = version;
        receivedPacketBytes = new ArrayList<>();

        serverSocket = new ServerSocket(port);

        listenExecutor = Executors.newSingleThreadExecutor();
        listenExecutor.execute(this::listen);
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
        var bytes = new PacketBuilder((short) 20, PacketType.VER, reqI < 0 ? (short) (reqI + 256) : (short) reqI)
                .writeZeroByte()
                .writeCharArray(version, 8)
                .writeCharArray(product.toString(), 6)
                .writeByte(9)
                .writeByte(0)
                .getBytes();
        out.write(bytes);
    }

    private void listen() {
        try {
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
