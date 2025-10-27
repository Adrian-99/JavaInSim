/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.api.insim.packets.enums.SshErrorCode;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketReader;
import com.github.adrian99.javainsim.testutil.AssertionUtils;
import com.github.adrian99.javainsim.testutil.MockedInSimConnection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static com.github.adrian99.javainsim.testutil.AssertionUtils.assertPacketHeaderEquals;

class SshPacketTest {
    @Test
    void createSshPacket() {
        var packet = new SshPacket(91, "screenshotName1");
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                10, 49, 91, 0, 0, 0, 0, 0, 115, 99, 114, 101, 101, 110, 115, 104,
                111, 116, 78, 97, 109, 101, 49, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0
        };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void readSshPacket() {
        var headerBytes = new byte[] { 10, 49, -112 };
        var dataBytes = new byte[] {
                3, 0, 0, 0, 0, 108, 102, 115, 95, 48, 48, 48, 48, 48, 48, 48,
                50, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(37, PacketType.SSH, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertInstanceOf(SshPacket.class, readPacket);

        var castedReadPacket = (SshPacket) readPacket;

        assertPacketHeaderEquals(40, PacketType.SSH, 144, castedReadPacket);
        Assertions.assertEquals(SshErrorCode.NO_SAVE, castedReadPacket.getError());
        assertEquals("lfs_00000002", castedReadPacket.getName());
    }

    @Test
    void requestSshPacket() throws IOException {
        var inSimConnectionMock = new MockedInSimConnection();

        SshPacket.request(inSimConnectionMock, new SshPacket(0, "img003"))
                .listen((inSimConnection, packet) -> {});

        var expectedRequestPacketBytes = new byte[] {
                10, 49, 0, 0, 0, 0, 0, 0, 105, 109, 103, 48, 48, 51, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0
        };
        AssertionUtils.assertRequestPacketBytesEqual(expectedRequestPacketBytes, inSimConnectionMock.assertAndPopSentPacketBytes());
    }
}
