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
import org.junit.jupiter.api.Test;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.insim.packets.enums.PenaltyValue;
import com.github.adrian99.javainsim.api.insim.packets.flags.PlayerFlag;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static com.github.adrian99.javainsim.testutil.AssertionUtils.assertPacketHeaderEquals;

class LapPacketTest {
    @Test
    void readLapPacket() {
        var headerBytes = new byte[] { 5, 24, 0 };
        var dataBytes = new byte[] {
                15, -12, -35, 0, 0, -15, 125, 3, 0, 4, 0, -111, 0, 0, 5, 1,
                115
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(17, PacketType.LAP, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof LapPacket);

        var castedReadPacket = (LapPacket) readPacket;

        assertPacketHeaderEquals(20, PacketType.LAP, 0, castedReadPacket);
        assertEquals(15, castedReadPacket.getPlid());
        assertEquals(56820, castedReadPacket.getLTime());
        assertEquals(228849, castedReadPacket.getETime());
        assertEquals(4, castedReadPacket.getLapsDone());
        AssertionUtils.assertFlagsEqual(
                PlayerFlag.class,
                Set.of(PlayerFlag.LEFTSIDE, PlayerFlag.SHIFTER, PlayerFlag.AXIS_CLUTCH),
                castedReadPacket.getFlags()
        );
        assertEquals(PenaltyValue.PLUS_30_S, castedReadPacket.getPenalty());
        assertEquals(1, castedReadPacket.getNumStops());
        assertEquals(115, castedReadPacket.getFuel200());
    }
}
