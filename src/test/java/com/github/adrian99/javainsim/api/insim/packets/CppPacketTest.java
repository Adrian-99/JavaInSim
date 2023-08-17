/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.api.common.flags.Flags;
import com.github.adrian99.javainsim.api.common.structures.Vec;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketReader;
import com.github.adrian99.javainsim.testutil.AssertionUtils;
import com.github.adrian99.javainsim.testutil.MockedInSimConnection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.insim.packets.enums.ViewIdentifier;
import com.github.adrian99.javainsim.api.insim.packets.flags.CppFlag;

import java.io.IOException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static com.github.adrian99.javainsim.testutil.AssertionUtils.*;

class CppPacketTest {
    @Test
    void createCppPacket() {
        var packet = new CppPacket(
                new Vec(162581, -15489656, 58934),
                1605,
                238,
                302,
                29,
                ViewIdentifier.CUSTOM,
                105,
                500,
                new Flags<>(CppFlag.SHIFTU, CppFlag.SHIFTU_FOLLOW)
        );
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                8, 9, 0, 0, 21, 123, 2, 0, -120, -91, 19, -1, 54, -26, 0, 0,
                69, 6, -18, 0, 46, 1, 29, 4, 0, 0, -46, 66, -12, 1, 40, 0
        };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void readCppPacket() {
        var headerBytes = new byte[] { 8, 9, -112 };
        var dataBytes = new byte[] {
                0, 98, 123, -40, -1, -78, -43, 121, 0, 86, 70, 1, 0, 26, 61, 69,
                1, 55, 0, 36, 2, 0, 0, -65, 66, -46, 10, 8, 0
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(29, PacketType.CPP, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof CppPacket);

        var castedReadPacket = (CppPacket) readPacket;

        assertPacketHeaderEquals(32, PacketType.CPP, 144, castedReadPacket);
        Assertions.assertEquals(-2589854, castedReadPacket.getPos().getX());
        Assertions.assertEquals(7984562, castedReadPacket.getPos().getY());
        Assertions.assertEquals(83542, castedReadPacket.getPos().getZ());
        assertEquals(15642, castedReadPacket.getH());
        assertEquals(325, castedReadPacket.getP());
        assertEquals(55, castedReadPacket.getR());
        assertEquals(36, castedReadPacket.getViewPlid());
        assertEquals(ViewIdentifier.CAM, castedReadPacket.getInGameCam());
        assertEquals(95.5, castedReadPacket.getFov());
        assertEquals(2770, castedReadPacket.getTime());
        AssertionUtils.assertFlagsEqual(CppFlag.class, Set.of(CppFlag.SHIFTU), castedReadPacket.getFlags());
    }

    @Test
    void requestCppPacket() throws IOException {
        var inSimConnectionMock = new MockedInSimConnection();

        CppPacket.request(inSimConnectionMock).listen(((inSimConnection, packet) -> {}));

        var expectedRequestPacketBytes = new byte[] { 1, 3, 0, 6 };
        AssertionUtils.assertRequestPacketBytesEqual(expectedRequestPacketBytes, inSimConnectionMock.assertAndGetSentPacketBytes());
    }
}
