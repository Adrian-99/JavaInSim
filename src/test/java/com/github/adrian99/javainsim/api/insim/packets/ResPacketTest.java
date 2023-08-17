/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.testutil.MockedInSimConnection;
import org.junit.jupiter.api.Test;
import com.github.adrian99.javainsim.api.common.enums.DefaultCar;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.insim.packets.flags.ConfirmationFlag;
import com.github.adrian99.javainsim.api.insim.packets.flags.PlayerFlag;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketReader;

import java.io.IOException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static com.github.adrian99.javainsim.testutil.AssertionUtils.*;
import static com.github.adrian99.javainsim.testutil.AssertionUtils.assertFlagsEqual;

class ResPacketTest {
    @Test
    void readResPacket() {
        var headerBytes = new byte[] { 21, 35, -112 };
        var dataBytes = new byte[] {
                39, 116, 104, 101, 95, 117, 115, 101, 114, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 94, 50, 85, 115, 101, 114, 94,
                51, 78, 97, 109, 101, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 85, 115, 114, 80, 108, 97, 116, 101, 76, 88, 52, 0, -72, 14, 32,
                0, 30, -36, 0, 0, 0, 5, 65, 0, 34, 0, -111, 1, 3, 15, 45,
                0
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(81, PacketType.RES, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof ResPacket);

        var castedReadPacket = (ResPacket) readPacket;

        assertPacketHeaderEquals(84, PacketType.RES, 144, castedReadPacket);
        assertEquals(39, castedReadPacket.getPlid());
        assertEquals("the_user", castedReadPacket.getUName());
        assertEquals("^2User^3Name", castedReadPacket.getPName());
        assertEquals("UsrPlate", castedReadPacket.getPlate());
        assertCarEquals(DefaultCar.LX4, castedReadPacket.getCar());
        assertEquals(2100920, castedReadPacket.getTTime());
        assertEquals(56350, castedReadPacket.getBTime());
        assertEquals(5, castedReadPacket.getNumStops());
        assertFlagsEqual(
                ConfirmationFlag.class,
                Set.of(ConfirmationFlag.MENTIONED, ConfirmationFlag.DID_NOT_PIT, ConfirmationFlag.DISQ),
                castedReadPacket.getConfirm()
        );
        assertEquals(34, castedReadPacket.getLapsDone());
        assertFlagsEqual(
                PlayerFlag.class,
                Set.of(PlayerFlag.LEFTSIDE, PlayerFlag.SHIFTER, PlayerFlag.AXIS_CLUTCH, PlayerFlag.INPITS),
                castedReadPacket.getFlags()
        );
        assertEquals(3, castedReadPacket.getResultNum());
        assertEquals(15, castedReadPacket.getNumRes());
        assertEquals(45, castedReadPacket.getPSeconds());
    }

    @Test
    void requestResPacket() throws IOException {
        var inSimConnectionMock = new MockedInSimConnection();

        ResPacket.request(inSimConnectionMock).listen(((inSimConnection, packet) -> {}));

        var expectedRequestPacketBytes = new byte[] { 1, 3, 0, 15 };
        assertRequestPacketBytesEqual(expectedRequestPacketBytes, inSimConnectionMock.assertAndGetSentPacketBytes());
    }
}
