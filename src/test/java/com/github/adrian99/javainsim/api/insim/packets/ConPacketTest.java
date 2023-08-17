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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.insim.packets.flags.CompCarFlag;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static com.github.adrian99.javainsim.testutil.AssertionUtils.assertPacketHeaderEquals;

class ConPacketTest {
    @Test
    @SuppressWarnings("java:S5961")
    void readConPacket() {
        var headerBytes = new byte[] { 10, 50, 0 };
        var dataBytes = new byte[] {
                0, -93, 2, -56, 72, 5, 32, 0, -16, 45, -16, 64, 36, 5, 8, -8,
                1, 52, 49, -98, -101, 12, 0, 0, 24, 13, -1, 48, 32, -7, -20, -9,
                3, 43, 49, -103, -101
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(37, PacketType.CON, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof ConPacket);

        var castedReadPacket = (ConPacket) readPacket;

        assertPacketHeaderEquals(40, PacketType.CON, 0, castedReadPacket);
        assertEquals(675, castedReadPacket.getClose());
        assertEquals(18632, castedReadPacket.getTime());
        Assertions.assertEquals(5, castedReadPacket.getA().getPlid());
        AssertionUtils.assertFlagsEqual(CompCarFlag.class, Set.of(CompCarFlag.LAG), castedReadPacket.getA().getInfo());
        Assertions.assertEquals(-16, castedReadPacket.getA().getSteer());
        Assertions.assertEquals(2, castedReadPacket.getA().getThr());
        Assertions.assertEquals(13, castedReadPacket.getA().getBrk());
        Assertions.assertEquals(15, castedReadPacket.getA().getClu());
        Assertions.assertEquals(0, castedReadPacket.getA().getHan());
        Assertions.assertEquals(4, castedReadPacket.getA().getGear());
        Assertions.assertEquals(36, castedReadPacket.getA().getSpeed());
        Assertions.assertEquals(5, castedReadPacket.getA().getDirection());
        Assertions.assertEquals(8, castedReadPacket.getA().getHeading());
        Assertions.assertEquals(-8, castedReadPacket.getA().getAccelF());
        Assertions.assertEquals(1, castedReadPacket.getA().getAccelR());
        Assertions.assertEquals(12596, castedReadPacket.getA().getX());
        Assertions.assertEquals(-25698, castedReadPacket.getA().getY());
        Assertions.assertEquals(12, castedReadPacket.getB().getPlid());
        AssertionUtils.assertFlagsEqual(CompCarFlag.class, Set.of(), castedReadPacket.getB().getInfo());
        Assertions.assertEquals(24, castedReadPacket.getB().getSteer());
        Assertions.assertEquals(0, castedReadPacket.getB().getThr());
        Assertions.assertEquals(13, castedReadPacket.getB().getBrk());
        Assertions.assertEquals(15, castedReadPacket.getB().getClu());
        Assertions.assertEquals(15, castedReadPacket.getB().getHan());
        Assertions.assertEquals(3, castedReadPacket.getB().getGear());
        Assertions.assertEquals(32, castedReadPacket.getB().getSpeed());
        Assertions.assertEquals(249, castedReadPacket.getB().getDirection());
        Assertions.assertEquals(236, castedReadPacket.getB().getHeading());
        Assertions.assertEquals(-9, castedReadPacket.getB().getAccelF());
        Assertions.assertEquals(3, castedReadPacket.getB().getAccelR());
        Assertions.assertEquals(12587, castedReadPacket.getB().getX());
        Assertions.assertEquals(-25703, castedReadPacket.getB().getY());
    }
}
