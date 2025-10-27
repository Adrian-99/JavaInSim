/*
 * Copyright (c) 2024, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.insim.packets.flags.PlayerHandicapsFlag;
import com.github.adrian99.javainsim.api.insim.packets.structures.PlayerHandicaps;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketReader;
import com.github.adrian99.javainsim.testutil.MockedInSimConnection;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static com.github.adrian99.javainsim.testutil.AssertionUtils.*;
import static org.junit.jupiter.api.Assertions.*;

class PlhPacketTest {
    @Test
    void createPlhPacket() {
        var packet = new PlhPacket(List.of(
                new PlayerHandicaps(15, false, null, 5),
                new PlayerHandicaps(21, true, 50, null),
                new PlayerHandicaps(36, true, 200, 25)
        ));
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                4, 66, 0, 3, 15, 2, 0, 5, 21, -127, 50, 0, 36, -125, -56, 25
        };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void readPlhPacket() {
        var headerBytes = new byte[] { 4, 66, -112 };
        var dataBytes = new byte[] { 3, 5, 2, 0, 5, 44, 1, -106, 0, 31, 3, 90, 10 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(13, PacketType.PLH, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertInstanceOf(PlhPacket.class, readPacket);

        var castedReadPacket = (PlhPacket) readPacket;

        assertPacketHeaderEquals(16, PacketType.PLH, 144, castedReadPacket);
        assertEquals(3, castedReadPacket.getNumP());
        assertEquals(5, castedReadPacket.getHCaps().get(0).getPlid());
        assertFlagsEqual(PlayerHandicapsFlag.class, Set.of(PlayerHandicapsFlag.SET_TRES), castedReadPacket.getHCaps().get(0).getFlags());
        assertEquals(0, castedReadPacket.getHCaps().get(0).getHMass());
        assertEquals(5, castedReadPacket.getHCaps().get(0).getHTRes());
        assertEquals(44, castedReadPacket.getHCaps().get(1).getPlid());
        assertFlagsEqual(PlayerHandicapsFlag.class, Set.of(PlayerHandicapsFlag.SET_MASS), castedReadPacket.getHCaps().get(1).getFlags());
        assertEquals(150, castedReadPacket.getHCaps().get(1).getHMass());
        assertEquals(0, castedReadPacket.getHCaps().get(1).getHTRes());
        assertEquals(31, castedReadPacket.getHCaps().get(2).getPlid());
        assertFlagsEqual(PlayerHandicapsFlag.class, Set.of(PlayerHandicapsFlag.SET_MASS, PlayerHandicapsFlag.SET_TRES), castedReadPacket.getHCaps().get(2).getFlags());
        assertEquals(90, castedReadPacket.getHCaps().get(2).getHMass());
        assertEquals(10, castedReadPacket.getHCaps().get(2).getHTRes());
    }

    @Test
    void requestPlhPacket() throws IOException {
        var inSimConnectionMock = new MockedInSimConnection();

        PlhPacket.request(inSimConnectionMock).listen((inSimConnection, packet) -> {});

        var expectedRequestPacketBytes = new byte[] { 1, 3, 0, 28 };
        assertRequestPacketBytesEqual(expectedRequestPacketBytes, inSimConnectionMock.assertAndPopSentPacketBytes());
    }
}
