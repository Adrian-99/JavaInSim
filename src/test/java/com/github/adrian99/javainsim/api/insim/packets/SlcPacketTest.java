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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.github.adrian99.javainsim.testutil.AssertionUtils.*;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class SlcPacketTest {
    @Test
    void readSlcPacket() {
        var headerBytes = new byte[] { 2, 62, -112 };
        var dataBytes = new byte[] { 9, -63, 104, 74, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(5, PacketType.SLC, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertInstanceOf(SlcPacket.class, readPacket);

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
        AssertionUtils.assertRequestPacketBytesEqual(expectedRequestPacketBytes, inSimConnectionMock.assertAndPopSentPacketBytes());
    }
}
