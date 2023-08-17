/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.internal.insim.packets.util.PacketReader;
import org.junit.jupiter.api.Test;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static com.github.adrian99.javainsim.testutil.AssertionUtils.assertPacketHeaderEquals;

class TocPacketTest {
    @Test
    void readTocPacket() {
        var headerBytes = new byte[] { 2, 31, 0 };
        var dataBytes = new byte[] { 7, 25, 31, 0, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(5, PacketType.TOC, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof TocPacket);

        var castedReadPacket = (TocPacket) readPacket;

        assertPacketHeaderEquals(8, PacketType.TOC, 0, castedReadPacket);
        assertEquals(7, castedReadPacket.getPlid());
        assertEquals(25, castedReadPacket.getOldUcid());
        assertEquals(31, castedReadPacket.getNewUcid());
    }
}
