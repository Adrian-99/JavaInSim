/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.insim.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.insim.packets.enums.BfnSubtype;
import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.internal.insim.packets.util.PacketReader;

import static org.junit.jupiter.api.Assertions.*;
import static pl.adrian.testutil.AssertionUtils.assertPacketHeaderEquals;

class BfnPacketTest {
    @Test
    void createBfnPacket() {
        var packet = new BfnPacket(BfnSubtype.DEL_BTN, 21, 15, 20);
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] { 2, 42, 0, 0, 21, 15, 20, 0 };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void readBfnPacket() {
        var headerBytes = new byte[] { 2, 42, 0 };
        var dataBytes = new byte[] { 2, 37, 0, 0, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(5, PacketType.BFN, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof BfnPacket);

        var castedReadPacket = (BfnPacket) readPacket;

        assertPacketHeaderEquals(8, PacketType.BFN, 0, castedReadPacket);
        assertEquals(BfnSubtype.USER_CLEAR, castedReadPacket.getSubT());
        assertEquals(37, castedReadPacket.getUcid());
    }
}
