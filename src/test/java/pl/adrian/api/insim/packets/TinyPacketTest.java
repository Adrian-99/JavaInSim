/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.insim.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.api.insim.packets.enums.TinySubtype;
import pl.adrian.internal.insim.packets.util.PacketReader;

import static org.junit.jupiter.api.Assertions.*;
import static pl.adrian.testutil.AssertionUtils.assertPacketHeaderEquals;

class TinyPacketTest {
    @Test
    void createTinyPacket() {
        var packet = new TinyPacket(150, TinySubtype.NONE);
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                1, 3, -106, 0
        };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void readTinyPacket() {
        var headerBytes = new byte[] { 1, 3, -112 };
        var dataBytes = new byte[] { 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(1, PacketType.TINY, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof TinyPacket);

        var castedReadPacket = (TinyPacket) readPacket;

        assertPacketHeaderEquals(4, PacketType.TINY, 144, castedReadPacket);
        assertEquals(TinySubtype.NONE, castedReadPacket.getSubT());
    }
}
