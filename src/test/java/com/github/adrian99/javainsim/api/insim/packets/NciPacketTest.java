/*
 * Copyright (c) 2024, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.api.insim.packets.enums.License;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketReader;
import com.github.adrian99.javainsim.testutil.AssertionUtils;
import com.github.adrian99.javainsim.testutil.MockedInSimConnection;
import org.junit.jupiter.api.Test;
import com.github.adrian99.javainsim.api.insim.packets.enums.Language;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;

import java.io.IOException;

import static com.github.adrian99.javainsim.testutil.AssertionUtils.assertPacketHeaderEquals;
import static org.junit.jupiter.api.Assertions.*;

class NciPacketTest {
    @Test
    void readNciPacket() {
        var headerBytes = new byte[] { 4, 57, -112 };
        var dataBytes = new byte[] { 31, 17, 2, 0, 0, 112, -42, 0, 0, 54, 120, -95, 64 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(13, PacketType.NCI, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertInstanceOf(NciPacket.class, readPacket);

        var castedReadPacket = (NciPacket) readPacket;

        assertPacketHeaderEquals(16, PacketType.NCI, 144, castedReadPacket);
        assertEquals(31, castedReadPacket.getUcid());
        assertEquals(Language.POLSKI, castedReadPacket.getLanguage());
        assertEquals(License.S2, castedReadPacket.getLicense());
        assertEquals(54896, castedReadPacket.getUserId());
        assertEquals(1084323894L, castedReadPacket.getIpAddress().getUnsignedValue());
        assertEquals("64.161.120.54", castedReadPacket.getIpAddress().getStringValue());
    }

    @Test
    void requestNciPacket() throws IOException {
        var inSimConnectionMock = new MockedInSimConnection();

        NciPacket.request(inSimConnectionMock).listen(((inSimConnection, packet) -> {}));

        var expectedRequestPacketBytes = new byte[] { 1, 3, 0, 23 };
        AssertionUtils.assertRequestPacketBytesEqual(expectedRequestPacketBytes, inSimConnectionMock.assertAndPopSentPacketBytes());
    }
}
