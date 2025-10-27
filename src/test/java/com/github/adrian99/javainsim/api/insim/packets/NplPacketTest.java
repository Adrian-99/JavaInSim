/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.api.common.enums.DefaultCar;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketReader;
import com.github.adrian99.javainsim.testutil.AssertionUtils;
import com.github.adrian99.javainsim.testutil.MockedInSimConnection;
import org.junit.jupiter.api.Test;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.insim.packets.enums.TyreCompound;
import com.github.adrian99.javainsim.api.insim.packets.flags.PassengerFlag;
import com.github.adrian99.javainsim.api.insim.packets.flags.PlayerFlag;
import com.github.adrian99.javainsim.api.insim.packets.flags.PlayerTypeFlag;
import com.github.adrian99.javainsim.api.insim.packets.flags.SetupFlag;

import java.io.IOException;
import java.util.Set;

import static com.github.adrian99.javainsim.testutil.AssertionUtils.*;
import static org.junit.jupiter.api.Assertions.*;

class NplPacketTest {
    @Test
    void readNplPacket() {
        var headerBytes = new byte[] { 19, 21, -112 };
        var dataBytes = new byte[] {
                57, 23, 6, 9, 50, 80, 108, 97, 121, 101, 114, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 77, 121, 32,
                80, 108, 97, 116, 101, 85, 70, 49, 0, 100, 101, 102, 97, 117, 108, 116,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 5, 5, 5, 0, 0, 0,
                100, 0, 0, 0, 0, 7, 3, 1, 54
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(73, PacketType.NPL, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertInstanceOf(NplPacket.class, readPacket);

        var castedReadPacket = (NplPacket) readPacket;

        assertPacketHeaderEquals(76, PacketType.NPL, 144, castedReadPacket);
        assertEquals(57, castedReadPacket.getPlid());
        assertEquals(23, castedReadPacket.getUcid());
        AssertionUtils.assertFlagsEqual(PlayerTypeFlag.class, Set.of(PlayerTypeFlag.AI, PlayerTypeFlag.REMOTE), castedReadPacket.getPType());
        AssertionUtils.assertFlagsEqual(
                PlayerFlag.class,
                Set.of(
                        PlayerFlag.LEFTSIDE,
                        PlayerFlag.AUTOGEARS,
                        PlayerFlag.AUTOCLUTCH,
                        PlayerFlag.KB_STABILISED,
                        PlayerFlag.CUSTOM_VIEW
                ),
                castedReadPacket.getFlags()
        );
        assertEquals("Player", castedReadPacket.getPName());
        assertEquals("My Plate", castedReadPacket.getPlate());
        assertCarEquals(DefaultCar.UF1, castedReadPacket.getCar());
        assertEquals("default", castedReadPacket.getSName());
        assertEquals(TyreCompound.ROAD_NORMAL, castedReadPacket.getTyres().getFrontLeft());
        assertEquals(TyreCompound.ROAD_NORMAL, castedReadPacket.getTyres().getFrontRight());
        assertEquals(TyreCompound.ROAD_NORMAL, castedReadPacket.getTyres().getRearLeft());
        assertEquals(TyreCompound.ROAD_NORMAL, castedReadPacket.getTyres().getRearRight());
        assertEquals(0, castedReadPacket.getHMass());
        assertEquals(0, castedReadPacket.getHTRes());
        assertEquals(0, castedReadPacket.getModel());
        AssertionUtils.assertFlagsEqual(
                PassengerFlag.class,
                Set.of(PassengerFlag.REAR_LEFT_MALE, PassengerFlag.REAR_MIDDLE_FEMALE, PassengerFlag.REAR_RIGHT_MALE),
                castedReadPacket.getPass()
        );
        assertEquals(0, castedReadPacket.getRWAdj());
        assertEquals(0, castedReadPacket.getFWAdj());
        AssertionUtils.assertFlagsEqual(
                SetupFlag.class,
                Set.of(SetupFlag.SYMM_WHEELS, SetupFlag.TC_ENABLE, SetupFlag.ABS_ENABLE),
                castedReadPacket.getSetF()
        );
        assertEquals(3, castedReadPacket.getNumP());
        assertEquals(1, castedReadPacket.getConfig());
        assertEquals(54, castedReadPacket.getFuel());
    }

    @Test
    void requestNplPacket() throws IOException {
        var inSimConnectionMock = new MockedInSimConnection();

        NplPacket.request(inSimConnectionMock).listen(((inSimConnection, packet) -> {}));

        var expectedRequestPacketBytes = new byte[] { 1, 3, 0, 14 };
        AssertionUtils.assertRequestPacketBytesEqual(expectedRequestPacketBytes, inSimConnectionMock.assertAndPopSentPacketBytes());
    }
}
