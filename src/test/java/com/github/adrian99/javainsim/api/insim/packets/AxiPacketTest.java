/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.internal.insim.packets.util.PacketReader;
import com.github.adrian99.javainsim.testutil.AssertionUtils;
import com.github.adrian99.javainsim.testutil.MockedInSimConnection;
import org.junit.jupiter.api.Test;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static com.github.adrian99.javainsim.testutil.AssertionUtils.assertPacketHeaderEquals;

class AxiPacketTest {
    @Test
    void readAxiPacket() {
        var headerBytes = new byte[] { 10, 43, -112 };
        var dataBytes = new byte[] {
                0, -74, 22, -84, 5, 116, 101, 115, 116, 95, 108, 97, 121, 111, 117, 116,
                95, 110, 97, 109, 101, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(37, PacketType.AXI, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertInstanceOf(AxiPacket.class, readPacket);

        var castedReadPacket = (AxiPacket) readPacket;

        assertPacketHeaderEquals(40, PacketType.AXI, 144, castedReadPacket);
        assertEquals(182, castedReadPacket.getAXStart());
        assertEquals(22, castedReadPacket.getNumCP());
        assertEquals(1452, castedReadPacket.getNumO());
        assertEquals("test_layout_name", castedReadPacket.getLName());
    }

    @Test
    void requestAxiPacket() throws IOException {
        var inSimConnectionMock = new MockedInSimConnection();

        AxiPacket.request(inSimConnectionMock).listen((connection, packet) -> {});

        var expectedRequestPacketBytes = new byte[] { 1, 3, 0, 20 };
        AssertionUtils.assertRequestPacketBytesEqual(expectedRequestPacketBytes, inSimConnectionMock.assertAndPopSentPacketBytes());
    }
}
