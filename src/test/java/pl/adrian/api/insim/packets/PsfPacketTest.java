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

class PsfPacketTest {
    @Test
    void readPsfPacket() {
        var headerBytes = new byte[] { 3, 27, 0 };
        var dataBytes = new byte[] {
                23, -96, 23, 2, 0, 0, 0, 0, 0
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(9, PacketType.PSF, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof PsfPacket);

        var castedReadPacket = (PsfPacket) readPacket;

        assertPacketHeaderEquals(12, PacketType.PSF, 0, castedReadPacket);
        assertEquals(23, castedReadPacket.getPlid());
        assertEquals(137120, castedReadPacket.getSTime());
    }
}
