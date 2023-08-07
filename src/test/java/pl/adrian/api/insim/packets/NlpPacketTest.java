/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.insim.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.internal.insim.packets.util.PacketReader;
import pl.adrian.testutil.MockedInSimConnection;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.adrian.testutil.AssertionUtils.assertPacketHeaderEquals;
import static pl.adrian.testutil.AssertionUtils.assertRequestPacketBytesEqual;

class NlpPacketTest {
    @Test
    void readNlpPacket() {
        var headerBytes = new byte[] { 6, 37, -112 };
        var dataBytes = new byte[] {
                3, -102, 0, 15, 0, 6, 2, 73, 1, 18, 0, 10, 1, 59, 0, 15,
                0, 2, 3, 0, 0
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(21, PacketType.NLP, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof NlpPacket);

        var castedReadPacket = (NlpPacket) readPacket;

        assertPacketHeaderEquals(24, PacketType.NLP, 144, castedReadPacket);
        assertEquals(3, castedReadPacket.getNumP());
        assertEquals(3, castedReadPacket.getInfo().size());
        assertEquals(154, castedReadPacket.getInfo().get(0).getNode());
        assertEquals(15, castedReadPacket.getInfo().get(0).getLap());
        assertEquals(6, castedReadPacket.getInfo().get(0).getPlid());
        assertEquals(2, castedReadPacket.getInfo().get(0).getPosition());
        assertEquals(329, castedReadPacket.getInfo().get(1).getNode());
        assertEquals(18, castedReadPacket.getInfo().get(1).getLap());
        assertEquals(10, castedReadPacket.getInfo().get(1).getPlid());
        assertEquals(1, castedReadPacket.getInfo().get(1).getPosition());
        assertEquals(59, castedReadPacket.getInfo().get(2).getNode());
        assertEquals(15, castedReadPacket.getInfo().get(2).getLap());
        assertEquals(2, castedReadPacket.getInfo().get(2).getPlid());
        assertEquals(3, castedReadPacket.getInfo().get(2).getPosition());
    }

    @Test
    void requestNlpPacket() throws IOException {
        var inSimConnectionMock = new MockedInSimConnection();

        NlpPacket.request(inSimConnectionMock).listen(((inSimConnection, packet) -> {}));

        var expectedRequestPacketBytes = new byte[] { 1, 3, 0, 16 };
        assertRequestPacketBytesEqual(expectedRequestPacketBytes, inSimConnectionMock.assertAndGetSentPacketBytes());
    }
}
