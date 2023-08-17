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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.github.adrian99.javainsim.testutil.AssertionUtils.assertPacketHeaderEquals;

class MalPacketTest {
    @Test
    void createMalPacket() {
        var packet = new MalPacket(List.of("31A475", "DB2790", "2C6037", "A03FE5"));
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                6, 65, 0, 4, 0, 0, 0, 0, 117, -92, 49, 0, -112, 39, -37, 0,
                55, 96, 44, 0, -27, 63, -96, 0
        };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void readMalPacket() {
        var headerBytes = new byte[] { 5, 65, -112 };
        var dataBytes = new byte[] { 3, 28, 0, 0, 0, 46, 71, 61, 0, 54, -84, 87, 0, -72, 65, -97, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(17, PacketType.MAL, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof MalPacket);

        var castedReadPacket = (MalPacket) readPacket;

        assertPacketHeaderEquals(20, PacketType.MAL, 144, castedReadPacket);
        assertEquals(3, castedReadPacket.getNumM());
        assertEquals(28, castedReadPacket.getUcid());
        assertEquals(3, castedReadPacket.getSkinId().size());
        assertEquals("3D472E", castedReadPacket.getSkinId().get(0));
        assertEquals("57AC36", castedReadPacket.getSkinId().get(1));
        assertEquals("9F41B8", castedReadPacket.getSkinId().get(2));
    }

    @Test
    void requestMalPacket() throws IOException {
        var inSimConnectionMock = new MockedInSimConnection();

        MalPacket.request(inSimConnectionMock).listen(((inSimConnection, packet) -> {}));

        var expectedRequestPacketBytes = new byte[] { 1, 3, 0, 27 };
        AssertionUtils.assertRequestPacketBytesEqual(expectedRequestPacketBytes, inSimConnectionMock.assertAndGetSentPacketBytes());
    }
}
