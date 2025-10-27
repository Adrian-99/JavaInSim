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

import java.io.IOException;

import static com.github.adrian99.javainsim.testutil.AssertionUtils.assertPacketHeaderEquals;
import static org.junit.jupiter.api.Assertions.*;

class IsmPacketTest {
    @Test
    void readIsmPacket() {
        var headerBytes = new byte[] { 10, 10, -112 };
        var dataBytes = new byte[] {
                0, 1, 0, 0, 0, 69, 120, 97, 109, 112, 108, 101, 32, 76, 70, 83,
                32, 83, 101, 114, 118, 101, 114, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(37, PacketType.ISM, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertInstanceOf(IsmPacket.class, readPacket);

        var castedReadPacket = (IsmPacket) readPacket;

        assertPacketHeaderEquals(40, PacketType.ISM, 144, castedReadPacket);
        assertTrue(castedReadPacket.isHost());
        assertEquals("Example LFS Server", castedReadPacket.getHName());
    }

    @Test
    void requestIsmPacket() throws IOException {
        var inSimConnectionMock = new MockedInSimConnection();

        IsmPacket.request(inSimConnectionMock).listen(((inSimConnection, packet) -> {}));

        var expectedRequestPacketBytes = new byte[] { 1, 3, 0, 10 };
        AssertionUtils.assertRequestPacketBytesEqual(expectedRequestPacketBytes, inSimConnectionMock.assertAndPopSentPacketBytes());
    }
}
