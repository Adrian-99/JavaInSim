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
import com.github.adrian99.javainsim.api.insim.packets.enums.Product;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static com.github.adrian99.javainsim.testutil.AssertionUtils.assertPacketHeaderEquals;

class VerPacketTest {
    @Test
    void readVerPacket() {
        var headerBytes = new byte[] { 5, 2, -112 };
        var dataBytes = new byte[] { 0, 48, 46, 55, 68, 0, 0, 0, 0, 83, 51, 0, 0, 0, 0, 9 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(17, PacketType.VER, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof VerPacket);

        var castedReadPacket = (VerPacket) readPacket;

        assertPacketHeaderEquals(20, PacketType.VER, 144, castedReadPacket);
        assertEquals("0.7D", castedReadPacket.getVersion());
        assertEquals(Product.S3, castedReadPacket.getProduct());
        assertEquals(9, castedReadPacket.getInSimVer());
    }

    @Test
    void requestVerPacket() throws IOException {
        var inSimConnectionMock = new MockedInSimConnection();

        VerPacket.request(inSimConnectionMock).listen(((inSimConnection, packet) -> {}));

        var expectedRequestPacketBytes = new byte[] { 1, 3, 0, 1 };
        AssertionUtils.assertRequestPacketBytesEqual(expectedRequestPacketBytes, inSimConnectionMock.assertAndGetSentPacketBytes());
    }
}
