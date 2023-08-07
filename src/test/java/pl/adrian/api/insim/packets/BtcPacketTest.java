/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.insim.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.api.insim.packets.flags.ButtonInstFlag;
import pl.adrian.api.insim.packets.flags.ClickFlag;
import pl.adrian.internal.insim.packets.util.PacketReader;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.adrian.testutil.AssertionUtils.assertFlagsEqual;
import static pl.adrian.testutil.AssertionUtils.assertPacketHeaderEquals;

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
        assertFlagsEqual(ButtonInstFlag.class, Set.of(ButtonInstFlag.ALWAYS_ON), castedReadPacket.getInst());
        assertFlagsEqual(ClickFlag.class, Set.of(ClickFlag.LMB, ClickFlag.CTRL), castedReadPacket.getCFlags());
    }
}
