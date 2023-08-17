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
import com.github.adrian99.javainsim.api.insim.packets.enums.MessageType;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static com.github.adrian99.javainsim.testutil.AssertionUtils.assertPacketHeaderEquals;

class MsoPacketTest {
    @Test
    void readMsoPacket() {
        var headerBytes = new byte[] { 7, 11, 0 };
        var dataBytes = new byte[] {
                0, 15, 65, 1, 10, 117, 115, 101, 114, 110, 97, 109, 101, 58, 32, 116,
                101, 115, 116, 32, 116, 101, 120, 116, 0
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(25, PacketType.MSO, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof MsoPacket);

        var castedReadPacket = (MsoPacket) readPacket;

        assertPacketHeaderEquals(28, PacketType.MSO, 0, castedReadPacket);
        assertEquals(15, castedReadPacket.getUcid());
        assertEquals(65, castedReadPacket.getPlid());
        assertEquals(MessageType.USER, castedReadPacket.getUserType());
        assertEquals(10, castedReadPacket.getTextStart());
        assertEquals("username: test text", castedReadPacket.getMsg());
    }
}
