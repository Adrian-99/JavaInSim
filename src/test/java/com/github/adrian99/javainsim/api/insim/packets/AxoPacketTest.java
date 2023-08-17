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

class AxoPacketTest {
    @Test
    void readAxoPacket() {
        var headerBytes = new byte[] { 1, 44, 0 };
        var dataBytes = new byte[] { 36 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(1, PacketType.AXO, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof AxoPacket);

        var castedReadPacket = (AxoPacket) readPacket;

        assertPacketHeaderEquals(4, PacketType.AXO, 0, castedReadPacket);
        assertEquals(36, castedReadPacket.getPlid());
    }
}
