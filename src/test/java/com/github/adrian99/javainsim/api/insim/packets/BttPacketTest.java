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
import com.github.adrian99.javainsim.api.insim.packets.flags.ButtonInstFlag;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static com.github.adrian99.javainsim.testutil.AssertionUtils.assertPacketHeaderEquals;

class BttPacketTest {
    @Test
    void readBttPacket() {
        var headerBytes = new byte[] { 26, 47, -112 };
        var dataBytes = new byte[] {
                27, -106, 0, -108, 0, 83, 111, 109, 101, 32, 84, 121, 112, 101, 100, 32,
                84, 101, 120, 116, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(101, PacketType.BTT, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof BttPacket);

        var castedReadPacket = (BttPacket) readPacket;

        assertPacketHeaderEquals(104, PacketType.BTT, 144, castedReadPacket);
        assertEquals(27, castedReadPacket.getUcid());
        assertEquals(150, castedReadPacket.getClickID());
        AssertionUtils.assertFlagsEqual(ButtonInstFlag.class, Set.of(), castedReadPacket.getInst());
        assertTrue(castedReadPacket.isInitializeWithButtonText());
        assertEquals(20, castedReadPacket.getTypeInCharacters());
        assertEquals("Some Typed Text", castedReadPacket.getText());
    }
}
