/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.api.insim.packets.flags.ClickFlag;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketReader;
import com.github.adrian99.javainsim.testutil.AssertionUtils;
import org.junit.jupiter.api.Test;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.insim.packets.flags.ButtonInstFlag;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static com.github.adrian99.javainsim.testutil.AssertionUtils.assertPacketHeaderEquals;

class BtcPacketTest {
    @Test
    void readBtcPacket() {
        var headerBytes = new byte[] { 2, 46, -112 };
        var dataBytes = new byte[] { 14, 98, -128, 5, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(5, PacketType.BTC, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof BtcPacket);

        var castedReadPacket = (BtcPacket) readPacket;

        assertPacketHeaderEquals(8, PacketType.BTC, 144, castedReadPacket);
        assertEquals(14, castedReadPacket.getUcid());
        assertEquals(98, castedReadPacket.getClickID());
        AssertionUtils.assertFlagsEqual(ButtonInstFlag.class, Set.of(ButtonInstFlag.ALWAYS_ON), castedReadPacket.getInst());
        AssertionUtils.assertFlagsEqual(ClickFlag.class, Set.of(ClickFlag.LMB, ClickFlag.CTRL), castedReadPacket.getCFlags());
    }
}
