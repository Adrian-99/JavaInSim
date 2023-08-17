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
import com.github.adrian99.javainsim.api.insim.packets.flags.PlayerFlag;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static com.github.adrian99.javainsim.testutil.AssertionUtils.assertPacketHeaderEquals;

class PflPacketTest {
    @Test
    void readPflPacket() {
        var headerBytes = new byte[] { 2, 33, 0 };
        var dataBytes = new byte[] { 22, 9, 11, 0, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(5, PacketType.PFL, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof PflPacket);

        var castedReadPacket = (PflPacket) readPacket;

        assertPacketHeaderEquals(8, PacketType.PFL, 0, castedReadPacket);
        assertEquals(22, castedReadPacket.getPlid());
        AssertionUtils.assertFlagsEqual(
                PlayerFlag.class,
                Set.of(
                        PlayerFlag.LEFTSIDE,
                        PlayerFlag.AUTOGEARS,
                        PlayerFlag.INPITS,
                        PlayerFlag.AUTOCLUTCH,
                        PlayerFlag.KB_NO_HELP
                ),
                castedReadPacket.getFlags()
        );
    }
}
