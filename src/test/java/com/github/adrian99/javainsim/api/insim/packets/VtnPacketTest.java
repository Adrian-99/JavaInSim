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
import com.github.adrian99.javainsim.api.insim.packets.enums.VoteAction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static com.github.adrian99.javainsim.testutil.AssertionUtils.assertPacketHeaderEquals;

class VtnPacketTest {
    @Test
    void readVtnPacket() {
        var headerBytes = new byte[] { 2, 16, 0 };
        var dataBytes = new byte[] { 0, 34, 2, 0, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(5, PacketType.VTN, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof VtnPacket);

        var castedReadPacket = (VtnPacket) readPacket;

        assertPacketHeaderEquals(8, PacketType.VTN, 0, castedReadPacket);
        assertEquals(34, castedReadPacket.getUcid());
        assertEquals(VoteAction.RESTART, castedReadPacket.getAction());
    }
}
