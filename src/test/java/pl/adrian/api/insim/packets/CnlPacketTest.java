/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.insim.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.insim.packets.enums.LeaveReason;
import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.internal.insim.packets.util.PacketReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.adrian.testutil.AssertionUtils.assertPacketHeaderEquals;

class CnlPacketTest {
    @Test
    void readCnlPacket() {
        var headerBytes = new byte[] { 2, 19, 0 };
        var dataBytes = new byte[] { 38, 3, 26, 0, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(5, PacketType.CNL, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof CnlPacket);

        var castedReadPacket = (CnlPacket) readPacket;

        assertPacketHeaderEquals(8, PacketType.CNL, 0, castedReadPacket);
        assertEquals(38, castedReadPacket.getUcid());
        assertEquals(LeaveReason.KICKED, castedReadPacket.getReason());
        assertEquals(26, castedReadPacket.getTotal());
    }
}
