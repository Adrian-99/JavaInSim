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
import com.github.adrian99.javainsim.api.insim.packets.enums.AcrResult;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static com.github.adrian99.javainsim.testutil.AssertionUtils.assertPacketHeaderEquals;

class AcrPacketTest {
    @Test
    void readAcrPacket() {
        var headerBytes = new byte[] { 5, 55, 0 };
        var dataBytes = new byte[] { 0, 31, 1, 3, 0, 47, 99, 111, 109, 109, 97, 110, 100, 0, 0, 0, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(17, PacketType.ACR, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof AcrPacket);

        var castedReadPacket = (AcrPacket) readPacket;

        assertPacketHeaderEquals(20, PacketType.ACR, 0, castedReadPacket);
        assertEquals(31, castedReadPacket.getUcid());
        assertTrue(castedReadPacket.isAdmin());
        assertEquals(AcrResult.UNKNOWN_COMMAND, castedReadPacket.getResult());
        assertEquals("/command", castedReadPacket.getText());
    }
}
