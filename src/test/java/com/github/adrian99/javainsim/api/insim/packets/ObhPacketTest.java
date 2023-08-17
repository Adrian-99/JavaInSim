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
import com.github.adrian99.javainsim.api.insim.packets.enums.ObjectType;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.insim.packets.flags.ObhFlag;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static com.github.adrian99.javainsim.testutil.AssertionUtils.assertPacketHeaderEquals;

class ObhPacketTest {
    @Test
    void readObhPacket() {
        var headerBytes = new byte[] { 6, 51, 0 };
        var dataBytes = new byte[] {
                25, 59, 1, -85, 30, 96, 90, 32, 5, -96, -24, 100, 41, -103, -24, 94,
                41, 4, 0, 20, 11
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(21, PacketType.OBH, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof ObhPacket);

        var castedReadPacket = (ObhPacket) readPacket;

        assertPacketHeaderEquals(24, PacketType.OBH, 0, castedReadPacket);
        assertEquals(25, castedReadPacket.getPlid());
        assertEquals(315, castedReadPacket.getClose());
        assertEquals(7851, castedReadPacket.getTime());
        Assertions.assertEquals(96, castedReadPacket.getC().getDirection());
        Assertions.assertEquals(90, castedReadPacket.getC().getHeading());
        Assertions.assertEquals(32, castedReadPacket.getC().getSpeed());
        Assertions.assertEquals(5, castedReadPacket.getC().getZByte());
        Assertions.assertEquals(-5984, castedReadPacket.getC().getX());
        Assertions.assertEquals(10596, castedReadPacket.getC().getY());
        assertEquals(-5991, castedReadPacket.getX());
        assertEquals(10590, castedReadPacket.getY());
        assertEquals(4, castedReadPacket.getZByte());
        assertEquals(ObjectType.CONE_RED, castedReadPacket.getIndex());
        AssertionUtils.assertFlagsEqual(
                ObhFlag.class,
                Set.of(ObhFlag.LAYOUT, ObhFlag.CAN_MOVE, ObhFlag.ON_SPOT),
                castedReadPacket.getObhFlags()
        );
    }
}
