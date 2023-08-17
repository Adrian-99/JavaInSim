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
import com.github.adrian99.javainsim.api.insim.packets.flags.CompCarFlag;

import java.io.IOException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static com.github.adrian99.javainsim.testutil.AssertionUtils.*;

class MciPacketTest {
    @Test
    @SuppressWarnings("java:S5961")
    void readMciPacket() {
        var headerBytes = new byte[] { 15, 38, -112 };
        var dataBytes = new byte[] {
                2, -84, 2, 9, 0, 17, 1, 96, 0, 102, -99, 79, 9, -113, -55, -40,
                -1, 82, -41, 1, 0, -32, 63, -28, 119, 42, 49, 71, 0, 102, 1, 9,
                0, 13, 2, -94, 0, -51, -70, -15, -1, 78, -108, -39, -1, 119, -26, -1,
                -1, 87, 41, -30, 61, -106, 58, -16, -1
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(57, PacketType.MCI, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof MciPacket);

        var castedReadPacket = (MciPacket) readPacket;

        assertPacketHeaderEquals(60, PacketType.MCI, 144, castedReadPacket);
        assertEquals(2, castedReadPacket.getNumC());
        assertEquals(2, castedReadPacket.getInfo().size());
        assertEquals(684, castedReadPacket.getInfo().get(0).getNode());
        assertEquals(9, castedReadPacket.getInfo().get(0).getLap());
        assertEquals(17, castedReadPacket.getInfo().get(0).getPlid());
        assertEquals(1, castedReadPacket.getInfo().get(0).getPosition());
        AssertionUtils.assertFlagsEqual(
                CompCarFlag.class,
                Set.of(CompCarFlag.LAG, CompCarFlag.FIRST),
                castedReadPacket.getInfo().get(0).getInfo()
        );
        assertEquals(156212582, castedReadPacket.getInfo().get(0).getX());
        assertEquals(-2569841, castedReadPacket.getInfo().get(0).getY());
        assertEquals(120658, castedReadPacket.getInfo().get(0).getZ());
        assertEquals(16352, castedReadPacket.getInfo().get(0).getSpeed());
        assertEquals(30692, castedReadPacket.getInfo().get(0).getDirection());
        assertEquals(12586, castedReadPacket.getInfo().get(0).getHeading());
        assertEquals(71, castedReadPacket.getInfo().get(0).getAngVel());
        assertEquals(358, castedReadPacket.getInfo().get(1).getNode());
        assertEquals(9, castedReadPacket.getInfo().get(1).getLap());
        assertEquals(13, castedReadPacket.getInfo().get(1).getPlid());
        assertEquals(2, castedReadPacket.getInfo().get(1).getPosition());
        AssertionUtils.assertFlagsEqual(
                CompCarFlag.class,
                Set.of(CompCarFlag.YELLOW, CompCarFlag.LAG, CompCarFlag.LAST),
                castedReadPacket.getInfo().get(1).getInfo()
        );
        assertEquals(-935219, castedReadPacket.getInfo().get(1).getX());
        assertEquals(-2517938, castedReadPacket.getInfo().get(1).getY());
        assertEquals(-6537, castedReadPacket.getInfo().get(1).getZ());
        assertEquals(10583, castedReadPacket.getInfo().get(1).getSpeed());
        assertEquals(15842, castedReadPacket.getInfo().get(1).getDirection());
        assertEquals(14998, castedReadPacket.getInfo().get(1).getHeading());
        assertEquals(-16, castedReadPacket.getInfo().get(1).getAngVel());
    }

    @Test
    void requestMciPacket() throws IOException {
        var inSimConnectionMock = new MockedInSimConnection();

        MciPacket.request(inSimConnectionMock).listen(((inSimConnection, packet) -> {}));

        var expectedRequestPacketBytes = new byte[] { 1, 3, 0, 17 };
        AssertionUtils.assertRequestPacketBytesEqual(expectedRequestPacketBytes, inSimConnectionMock.assertAndGetSentPacketBytes());
    }
}
