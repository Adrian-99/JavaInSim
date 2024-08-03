/*
 * Copyright (c) 2024, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.insim.packets.structures.IPAddress;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketReader;
import com.github.adrian99.javainsim.testutil.MockedInSimConnection;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static com.github.adrian99.javainsim.testutil.AssertionUtils.assertPacketHeaderEquals;
import static com.github.adrian99.javainsim.testutil.AssertionUtils.assertRequestPacketBytesEqual;
import static org.junit.jupiter.api.Assertions.*;

class IpbPacketTest {
    @Test
    void createIpbPacket() {
        var packet = new IpbPacket(List.of(
                new IPAddress("54.120.161.64"),
                new IPAddress("178.24.3.202")
        ));
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                4, 67, 0, 2, 0, 0, 0, 0, 64, -95, 120, 54, -54, 3, 24, -78
        };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void readIpbPacket() {
        var headerBytes = new byte[] { 4, 67, -112 };
        var dataBytes = new byte[] { 2, 0, 0, 0, 0, -95, 120, 54, -54, 64, 3, 24, -78 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(13, PacketType.IPB, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertInstanceOf(IpbPacket.class, readPacket);

        var castedReadPacket = (IpbPacket) readPacket;

        assertPacketHeaderEquals(16, PacketType.IPB, 144, castedReadPacket);
        assertEquals(2, castedReadPacket.getNumB());
        assertEquals(3392567457L, castedReadPacket.getBanIPs().get(0).getUnsignedValue());
        assertEquals("202.54.120.161", castedReadPacket.getBanIPs().get(0).getStringValue());
        assertEquals(2987918144L, castedReadPacket.getBanIPs().get(1).getUnsignedValue());
        assertEquals("178.24.3.64", castedReadPacket.getBanIPs().get(1).getStringValue());
    }

    @Test
    void requestIpbPacket() throws IOException {
        var inSimConnectionMock = new MockedInSimConnection();

        IpbPacket.request(inSimConnectionMock).listen((inSimConnection, packet) -> {});

        var expectedRequestPacketBytes = new byte[] { 1, 3, 0, 29 };
        assertRequestPacketBytesEqual(expectedRequestPacketBytes, inSimConnectionMock.assertAndGetSentPacketBytes());
    }
}
