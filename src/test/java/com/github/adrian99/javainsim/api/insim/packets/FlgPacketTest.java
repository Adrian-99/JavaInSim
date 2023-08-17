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
import com.github.adrian99.javainsim.api.insim.packets.enums.FlagType;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static com.github.adrian99.javainsim.testutil.AssertionUtils.assertPacketHeaderEquals;

class FlgPacketTest {
    @Test
    void readFlgPacket() {
        var headerBytes = new byte[] { 2, 32, 0 };
        var dataBytes = new byte[] { 19, 1, 2, 15, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(5, PacketType.FLG, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof FlgPacket);

        var castedReadPacket = (FlgPacket) readPacket;

        assertPacketHeaderEquals(8, PacketType.FLG, 0, castedReadPacket);
        assertEquals(19, castedReadPacket.getPlid());
        assertTrue(castedReadPacket.isOn());
        assertEquals(FlagType.CAUSING_YELLOW, castedReadPacket.getFlag());
        assertEquals(15, castedReadPacket.getCarBehind());
    }
}
