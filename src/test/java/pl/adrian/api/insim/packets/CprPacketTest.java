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
import pl.adrian.internal.insim.packets.util.PacketReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.adrian.testutil.AssertionUtils.assertPacketHeaderEquals;

class CprPacketTest {
    @Test
    void readCprPacket() {
        var headerBytes = new byte[] { 9, 20, 0 };
        var dataBytes = new byte[] {
                21, 94, 49, 78, 101, 119, 32, 94, 50, 80, 108, 97, 121, 101, 114, 32,
                94, 51, 78, 97, 109, 101, 0, 0, 0, 65, 68, 51, 54, 32, 82, 70,
                89
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(33, PacketType.CPR, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof CprPacket);

        var castedReadPacket = (CprPacket) readPacket;

        assertPacketHeaderEquals(36, PacketType.CPR, 0, castedReadPacket);
        assertEquals(21, castedReadPacket.getUcid());
        assertEquals("^1New ^2Player ^3Name", castedReadPacket.getPName());
        assertEquals("AD36 RFY", castedReadPacket.getPlate());
    }
}
