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
import static pl.adrian.testutil.AssertionUtils.*;

class SlcPacketTest {
    @Test
    void readSlcPacket() {
        var headerBytes = new byte[] { 2, 62, -112 };
        var dataBytes = new byte[] { 9, -63, 104, 74, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(5, PacketType.SLC, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof SlcPacket);

        var castedReadPacket = (SlcPacket) readPacket;

        assertPacketHeaderEquals(8, PacketType.SLC, 144, castedReadPacket);
        assertEquals(9, castedReadPacket.getUcid());
        assertCarEquals("4A68C1", castedReadPacket.getCar());
    }

    @Test
    void requestSlcPacket() throws IOException {
        var inSimConnectionMock = new MockedInSimConnection();

        SlcPacket.request(inSimConnectionMock).listen(((inSimConnection, packet) -> {}));

        var expectedRequestPacketBytes = new byte[] { 1, 3, 0, 26 };
        assertRequestPacketBytesEqual(expectedRequestPacketBytes, inSimConnectionMock.assertAndGetSentPacketBytes());
    }
}
