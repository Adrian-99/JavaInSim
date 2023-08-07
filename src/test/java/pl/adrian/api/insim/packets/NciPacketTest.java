/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.insim.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.insim.packets.enums.Language;
import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.internal.insim.packets.util.PacketReader;
import pl.adrian.testutil.MockedInSimConnection;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.adrian.testutil.AssertionUtils.assertPacketHeaderEquals;
import static pl.adrian.testutil.AssertionUtils.assertRequestPacketBytesEqual;

class NciPacketTest {
    @Test
    void readNciPacket() {
        var headerBytes = new byte[] { 4, 57, -112 };
        var dataBytes = new byte[] { 31, 17, 0, 0, 0, 112, -42, 0, 0, 54, 120, -95, 64 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(13, PacketType.NCI, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof NciPacket);

        var castedReadPacket = (NciPacket) readPacket;

        assertPacketHeaderEquals(16, PacketType.NCI, 144, castedReadPacket);
        assertEquals(31, castedReadPacket.getUcid());
        assertEquals(Language.POLSKI, castedReadPacket.getLanguage());
        assertEquals(54896, castedReadPacket.getUserId());
        assertEquals(1084323894, castedReadPacket.getIpAddress());
    }

    @Test
    void requestNciPacket() throws IOException {
        var inSimConnectionMock = new MockedInSimConnection();

        NciPacket.request(inSimConnectionMock).listen(((inSimConnection, packet) -> {}));

        var expectedRequestPacketBytes = new byte[] { 1, 3, 0, 23 };
        assertRequestPacketBytesEqual(expectedRequestPacketBytes, inSimConnectionMock.assertAndGetSentPacketBytes());
    }
}
