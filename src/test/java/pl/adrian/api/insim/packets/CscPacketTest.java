/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.insim.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.insim.packets.enums.CscAction;
import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.internal.insim.packets.util.PacketReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.adrian.testutil.AssertionUtils.assertPacketHeaderEquals;

class CscPacketTest {
    @Test
    void readCscPacket() {
        var headerBytes = new byte[] { 5, 63, 0 };
        var dataBytes = new byte[] {
                14, 0, 1, 0, 0, -20, 97, -63, 0, 76, 68, 28, 21, -89, -73, 114,
                24
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(17, PacketType.CSC, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof CscPacket);

        var castedReadPacket = (CscPacket) readPacket;

        assertPacketHeaderEquals(20, PacketType.CSC, 0, castedReadPacket);
        assertEquals(14, castedReadPacket.getPlid());
        assertEquals(CscAction.START, castedReadPacket.getCscAction());
        assertEquals(12673516, castedReadPacket.getTime());
        assertEquals(76, castedReadPacket.getC().getDirection());
        assertEquals(68, castedReadPacket.getC().getHeading());
        assertEquals(28, castedReadPacket.getC().getSpeed());
        assertEquals(21, castedReadPacket.getC().getZByte());
        assertEquals(-18521, castedReadPacket.getC().getX());
        assertEquals(6258, castedReadPacket.getC().getY());
    }
}
