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
import com.github.adrian99.javainsim.api.insim.packets.enums.TyreCompound;
import com.github.adrian99.javainsim.api.insim.packets.flags.PitWorkFlag;
import com.github.adrian99.javainsim.api.insim.packets.flags.PlayerFlag;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static com.github.adrian99.javainsim.testutil.AssertionUtils.assertPacketHeaderEquals;

class PitPacketTest {
    @Test
    void readPitPacket() {
        var headerBytes = new byte[] { 6, 26, 0 };
        var dataBytes = new byte[] {
                12, 16, 0, 72, 6, 10, 2, 3, 0, 7, -1, -1, 7, -62, -116, 0,
                0, 0, 0, 0, 0
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(21, PacketType.PIT, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof PitPacket);

        var castedReadPacket = (PitPacket) readPacket;

        assertPacketHeaderEquals(24, PacketType.PIT, 0, castedReadPacket);
        assertEquals(12, castedReadPacket.getPlid());
        assertEquals(16, castedReadPacket.getLapsDone());
        AssertionUtils.assertFlagsEqual(
                PlayerFlag.class,
                Set.of(PlayerFlag.AUTOGEARS, PlayerFlag.HELP_B, PlayerFlag.AUTOCLUTCH, PlayerFlag.MOUSE),
                castedReadPacket.getFlags()
        );
        assertEquals(10, castedReadPacket.getFuelAdd());
        assertEquals(PenaltyValue.DT_VALID, castedReadPacket.getPenalty());
        assertEquals(3, castedReadPacket.getNumStops());
        assertEquals(TyreCompound.KNOBBLY, castedReadPacket.getTyres().getFrontRight());
        assertEquals(TyreCompound.NOT_CHANGED, castedReadPacket.getTyres().getFrontLeft());
        assertEquals(TyreCompound.NOT_CHANGED, castedReadPacket.getTyres().getRearRight());
        assertEquals(TyreCompound.KNOBBLY, castedReadPacket.getTyres().getRearLeft());
        AssertionUtils.assertFlagsEqual(
                PitWorkFlag.class,
                Set.of(
                        PitWorkFlag.STOP,
                        PitWorkFlag.RI_FR_DAM,
                        PitWorkFlag.RI_FR_WHL,
                        PitWorkFlag.LE_RE_DAM,
                        PitWorkFlag.LE_RE_WHL,
                        PitWorkFlag.BODY_MAJOR
                ),
                castedReadPacket.getWork()
        );
    }
}
