/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.internal.insim.packets.util.PacketReader;
import com.github.adrian99.javainsim.testutil.AssertionUtils;
import com.github.adrian99.javainsim.testutil.MockedInSimConnection;
import org.junit.jupiter.api.Test;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.insim.packets.flags.NcnFlag;

import java.io.IOException;
import java.util.Set;

import static com.github.adrian99.javainsim.testutil.AssertionUtils.*;
import static org.junit.jupiter.api.Assertions.*;

class NcnPacketTest {
    @Test
    void readNcnPacket() {
        var headerBytes = new byte[] { 14, 18, -112 };
        var dataBytes = new byte[] {
                21, 116, 104, 101, 117, 115, 101, 114, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 78, 101, 119, 32, 85, 115, 101,
                114, 32, 78, 105, 99, 107, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 1, 34, 4, 0
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(53, PacketType.NCN, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertInstanceOf(NcnPacket.class, readPacket);

        var castedReadPacket = (NcnPacket) readPacket;

        assertPacketHeaderEquals(56, PacketType.NCN, 144, castedReadPacket);
        assertEquals(21, castedReadPacket.getUcid());
        assertEquals("theuser", castedReadPacket.getUName());
        assertEquals("New User Nick", castedReadPacket.getPName());
        assertTrue(castedReadPacket.isAdmin());
        assertEquals(34, castedReadPacket.getTotal());
        AssertionUtils.assertFlagsEqual(NcnFlag.class, Set.of(NcnFlag.REMOTE), castedReadPacket.getFlags());
    }

    @Test
    void requestNcnPacket() throws IOException {
        var inSimConnectionMock = new MockedInSimConnection();

        NcnPacket.request(inSimConnectionMock).listen(((inSimConnection, packet) -> {}));

        var expectedRequestPacketBytes = new byte[] { 1, 3, 0, 13 };
        AssertionUtils.assertRequestPacketBytesEqual(expectedRequestPacketBytes, inSimConnectionMock.assertAndPopSentPacketBytes());
    }
}
