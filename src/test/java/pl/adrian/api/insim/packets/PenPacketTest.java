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
import pl.adrian.api.insim.packets.enums.PenaltyReason;
import pl.adrian.api.insim.packets.enums.PenaltyValue;
import pl.adrian.internal.insim.packets.util.PacketReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.adrian.testutil.AssertionUtils.assertPacketHeaderEquals;

class PenPacketTest {
    @Test
    void readPenPacket() {
        var headerBytes = new byte[] { 2, 30, 0 };
        var dataBytes = new byte[] { 34, 0, 6, 3, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(5, PacketType.PEN, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof PenPacket);

        var castedReadPacket = (PenPacket) readPacket;

        assertPacketHeaderEquals(8, PacketType.PEN, 0, castedReadPacket);
        assertEquals(34, castedReadPacket.getPlid());
        assertEquals(PenaltyValue.NONE, castedReadPacket.getOldPen());
        assertEquals(PenaltyValue.PLUS_45_S, castedReadPacket.getNewPen());
        assertEquals(PenaltyReason.FALSE_START, castedReadPacket.getReason());
    }
}
