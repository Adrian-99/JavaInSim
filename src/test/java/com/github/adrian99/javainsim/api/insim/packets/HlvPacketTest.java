/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.internal.insim.packets.util.PacketReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.github.adrian99.javainsim.api.insim.packets.enums.Hlvc;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static com.github.adrian99.javainsim.testutil.AssertionUtils.assertPacketHeaderEquals;

class HlvPacketTest {
    @Test
    void readHlvPacket() {
        var headerBytes = new byte[] { 4, 52, 0 };
        var dataBytes = new byte[] {
                38, 4, 0, -87, 26, 102, 97, 23, 15, -109, -128, -16, -22
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(13, PacketType.HLV, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof HlvPacket);

        var castedReadPacket = (HlvPacket) readPacket;

        assertPacketHeaderEquals(16, PacketType.HLV, 0, castedReadPacket);
        assertEquals(38, castedReadPacket.getPlid());
        assertEquals(Hlvc.SPEEDING, castedReadPacket.getHlvc());
        assertEquals(6825, castedReadPacket.getTime());
        Assertions.assertEquals(102, castedReadPacket.getC().getDirection());
        Assertions.assertEquals(97, castedReadPacket.getC().getHeading());
        Assertions.assertEquals(23, castedReadPacket.getC().getSpeed());
        Assertions.assertEquals(15, castedReadPacket.getC().getZByte());
        Assertions.assertEquals(-32621, castedReadPacket.getC().getX());
        Assertions.assertEquals(-5392, castedReadPacket.getC().getY());
    }
}
