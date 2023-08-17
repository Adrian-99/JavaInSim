/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.api.insim.packets.structures.objectinfo.InSimCheckpointInfo;
import com.github.adrian99.javainsim.api.insim.packets.structures.objectinfo.InSimCircleInfo;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.github.adrian99.javainsim.api.insim.packets.enums.InSimCheckpointType;
import com.github.adrian99.javainsim.api.insim.packets.enums.ObjectType;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.insim.packets.enums.UcoAction;

import static org.junit.jupiter.api.Assertions.*;
import static com.github.adrian99.javainsim.testutil.AssertionUtils.assertPacketHeaderEquals;

class UcoPacketTest {
    @Test
    void readUcoPacket_forInSimCheckpoint() {
        var headerBytes = new byte[] { 7, 59, 0 };
        var dataBytes = new byte[] {
                26, 0, 0, 0, 0, -127, 52, 39, 0, 115, 112, 24, 13, 80, 84, -62,
                -67, 68, 84, -73, -67, 12, -67, -4, 94
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(25, PacketType.UCO, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof UcoPacket);

        var castedReadPacket = (UcoPacket) readPacket;

        assertPacketHeaderEquals(28, PacketType.UCO, 0, castedReadPacket);
        assertEquals(26, castedReadPacket.getPlid());
        assertEquals(UcoAction.CIRCLE_ENTER, castedReadPacket.getUcoAction());
        assertEquals(2569345, castedReadPacket.getTime());
        Assertions.assertEquals(115, castedReadPacket.getC().getDirection());
        Assertions.assertEquals(112, castedReadPacket.getC().getHeading());
        Assertions.assertEquals(24, castedReadPacket.getC().getSpeed());
        Assertions.assertEquals(13, castedReadPacket.getC().getZByte());
        Assertions.assertEquals(21584, castedReadPacket.getC().getX());
        Assertions.assertEquals(-16958, castedReadPacket.getC().getY());

        assertTrue(castedReadPacket.getInfo() instanceof InSimCheckpointInfo);
        var info = (InSimCheckpointInfo) castedReadPacket.getInfo();

        assertEquals(21572, info.getX());
        assertEquals(-16969, info.getY());
        assertEquals(12, info.getZByte());
        assertTrue(info.isFloating());
        assertEquals(InSimCheckpointType.CHECKPOINT_1, info.getCheckpointIndex());
        assertEquals(30, info.getWidth());
        assertEquals(ObjectType.MARSH_IS_CP, info.getIndex());
        assertEquals(94, info.getHeading());
    }

    @Test
    void readUcoPacket_forInSimCircle() {
        var headerBytes = new byte[] { 7, 59, 0 };
        var dataBytes = new byte[] {
                26, 0, 3, 0, 0, -127, 52, 39, 0, 115, 112, 24, 13, 80, 84, -62,
                -67, 68, 84, -73, -67, 12, 20, -3, -106
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(25, PacketType.UCO, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof UcoPacket);

        var castedReadPacket = (UcoPacket) readPacket;

        assertPacketHeaderEquals(28, PacketType.UCO, 0, castedReadPacket);
        assertEquals(26, castedReadPacket.getPlid());
        assertEquals(UcoAction.CP_REV, castedReadPacket.getUcoAction());
        assertEquals(2569345, castedReadPacket.getTime());
        Assertions.assertEquals(115, castedReadPacket.getC().getDirection());
        Assertions.assertEquals(112, castedReadPacket.getC().getHeading());
        Assertions.assertEquals(24, castedReadPacket.getC().getSpeed());
        Assertions.assertEquals(13, castedReadPacket.getC().getZByte());
        Assertions.assertEquals(21584, castedReadPacket.getC().getX());
        Assertions.assertEquals(-16958, castedReadPacket.getC().getY());

        assertTrue(castedReadPacket.getInfo() instanceof InSimCircleInfo);
        var info = (InSimCircleInfo) castedReadPacket.getInfo();

        assertEquals(21572, info.getX());
        assertEquals(-16969, info.getY());
        assertEquals(12, info.getZByte());
        assertFalse(info.isFloating());
        assertEquals(10, info.getDiameter());
        assertEquals(ObjectType.MARSH_IS_AREA, info.getIndex());
        assertEquals(150, info.getCircleIndex());
    }
}
