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
import pl.adrian.internal.insim.packets.util.PacketReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.adrian.testutil.AssertionUtils.assertPacketHeaderEquals;

class PllPacketTest {
    @Test
    void readPllPacket() {
        var headerBytes = new byte[] { 1, 23, 0 };
        var dataBytes = new byte[] { 26 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(1, PacketType.PLL, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof PllPacket);

        var castedReadPacket = (PllPacket) readPacket;

        assertPacketHeaderEquals(4, PacketType.PLL, 0, castedReadPacket);
        assertEquals(26, castedReadPacket.getPlid());
    }
}
