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

class CrsPacketTest {
    @Test
    void readCrsPacket() {
        var headerBytes = new byte[] { 1, 41, 0 };
        var dataBytes = new byte[] { 5 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(1, PacketType.CRS, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof CrsPacket);

        var castedReadPacket = (CrsPacket) readPacket;

        assertPacketHeaderEquals(4, PacketType.CRS, 0, castedReadPacket);
        assertEquals(5, castedReadPacket.getPlid());
    }
}
