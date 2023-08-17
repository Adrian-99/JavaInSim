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
import com.github.adrian99.javainsim.api.insim.packets.flags.ConfirmationFlag;
import com.github.adrian99.javainsim.api.insim.packets.flags.PlayerFlag;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static com.github.adrian99.javainsim.testutil.AssertionUtils.assertPacketHeaderEquals;

class FinPacketTest {
    @Test
    void readFinPacket() {
        var headerBytes = new byte[] { 5, 34, 0 };
        var dataBytes = new byte[] {
                25, 60, 93, 57, 0, 78, 1, 1, 0, 0, 4, 18, 0, 55, 0, 1,
                38
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(17, PacketType.FIN, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof FinPacket);

        var castedReadPacket = (FinPacket) readPacket;

        assertPacketHeaderEquals(20, PacketType.FIN, 0, castedReadPacket);
        assertEquals(25, castedReadPacket.getPlid());
        assertEquals(3759420, castedReadPacket.getTTime());
        assertEquals(65870, castedReadPacket.getBTime());
        assertEquals(4, castedReadPacket.getNumStops());
        AssertionUtils.assertFlagsEqual(
                ConfirmationFlag.class,
                Set.of(ConfirmationFlag.CONFIRMED, ConfirmationFlag.PENALTY_30, ConfirmationFlag.TIME),
                castedReadPacket.getConfirm()
        );
        assertEquals(55, castedReadPacket.getLapsDone());
        AssertionUtils.assertFlagsEqual(
                PlayerFlag.class,
                Set.of(PlayerFlag.LEFTSIDE, PlayerFlag.AUTOCLUTCH, PlayerFlag.MOUSE, PlayerFlag.CUSTOM_VIEW),
                castedReadPacket.getFlags()
        );
    }
}
