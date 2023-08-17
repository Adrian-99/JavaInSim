/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.testutil;

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
