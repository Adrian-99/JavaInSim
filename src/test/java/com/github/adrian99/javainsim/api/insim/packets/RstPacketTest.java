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
import com.github.adrian99.javainsim.api.insim.packets.enums.LapTimingType;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.insim.packets.enums.RaceLapsUnit;
import com.github.adrian99.javainsim.api.insim.packets.enums.Wind;
import com.github.adrian99.javainsim.api.insim.packets.flags.RaceFlag;

import java.io.IOException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.github.adrian99.javainsim.testutil.AssertionUtils.*;

class RstPacketTest {
    @Test
    void readRstPacket_withStandardLapTiming() {
        var headerBytes = new byte[] { 7, 17, -112 };
        var dataBytes = new byte[] {
                0, 15, 0, 20, 67, 70, 69, 49, 82, 0, 0, 1, 2, 33, 1, -24,
                3, 0, 0, -32, 0, 77, 2, -67, 2
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(25, PacketType.RST, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof RstPacket);

        var castedReadPacket = (RstPacket) readPacket;

        assertPacketHeaderEquals(28, PacketType.RST, 144, castedReadPacket);
        assertEquals(RaceLapsUnit.LAPS, castedReadPacket.getRaceLaps().getUnit());
        assertEquals(15, castedReadPacket.getRaceLaps().getValue());
        assertEquals(0, castedReadPacket.getQualMins());
        assertEquals(20, castedReadPacket.getNumP());
        assertEquals(LapTimingType.STANDARD, castedReadPacket.getTiming().getType());
        assertEquals(3, castedReadPacket.getTiming().getNumberOfCheckpoints());
        assertEquals("FE1R", castedReadPacket.getTrack());
        assertEquals(1, castedReadPacket.getWeather());
        assertEquals(Wind.STRONG, castedReadPacket.getWind());
        AssertionUtils.assertFlagsEqual(
                RaceFlag.class,
                Set.of(RaceFlag.CAN_VOTE, RaceFlag.MID_RACE, RaceFlag.FCV),
                castedReadPacket.getFlags()
        );
        assertEquals(1000, castedReadPacket.getNumNodes());
        assertEquals(0, castedReadPacket.getFinish());
        assertEquals(224, castedReadPacket.getSplit1());
        assertEquals(589, castedReadPacket.getSplit2());
        assertEquals(701, castedReadPacket.getSplit3());
    }

    @Test
    void readRstPacket_withCustomLapTiming() {
        var headerBytes = new byte[] { 7, 17, -112 };
        var dataBytes = new byte[] {
                0, 15, 0, 20, -126, 70, 69, 49, 82, 0, 0, 1, 2, 33, 1, -24,
                3, 0, 0, -32, 0, 77, 2, -67, 2
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(25, PacketType.RST, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof RstPacket);

        var castedReadPacket = (RstPacket) readPacket;

        assertPacketHeaderEquals(28, PacketType.RST, 144, castedReadPacket);
        assertEquals(RaceLapsUnit.LAPS, castedReadPacket.getRaceLaps().getUnit());
        assertEquals(15, castedReadPacket.getRaceLaps().getValue());
        assertEquals(0, castedReadPacket.getQualMins());
        assertEquals(20, castedReadPacket.getNumP());
        assertEquals(LapTimingType.CUSTOM, castedReadPacket.getTiming().getType());
        assertEquals(2, castedReadPacket.getTiming().getNumberOfCheckpoints());
        assertEquals("FE1R", castedReadPacket.getTrack());
        assertEquals(1, castedReadPacket.getWeather());
        assertEquals(Wind.STRONG, castedReadPacket.getWind());
        AssertionUtils.assertFlagsEqual(
                RaceFlag.class,
                Set.of(RaceFlag.CAN_VOTE, RaceFlag.MID_RACE, RaceFlag.FCV),
                castedReadPacket.getFlags()
        );
        assertEquals(1000, castedReadPacket.getNumNodes());
        assertEquals(0, castedReadPacket.getFinish());
        assertEquals(224, castedReadPacket.getSplit1());
        assertEquals(589, castedReadPacket.getSplit2());
        assertEquals(701, castedReadPacket.getSplit3());
    }

    @Test
    void readRstPacket_withNoLapTiming() {
        var headerBytes = new byte[] { 7, 17, -112 };
        var dataBytes = new byte[] {
                0, 15, 0, 20, -64, 70, 69, 49, 82, 0, 0, 1, 2, 33, 1, -24,
                3, 0, 0, -32, 0, 77, 2, -67, 2
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(25, PacketType.RST, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof RstPacket);

        var castedReadPacket = (RstPacket) readPacket;

        assertPacketHeaderEquals(28, PacketType.RST, 144, castedReadPacket);
        assertEquals(RaceLapsUnit.LAPS, castedReadPacket.getRaceLaps().getUnit());
        assertEquals(15, castedReadPacket.getRaceLaps().getValue());
        assertEquals(0, castedReadPacket.getQualMins());
        assertEquals(20, castedReadPacket.getNumP());
        assertEquals(LapTimingType.NONE, castedReadPacket.getTiming().getType());
        assertEquals(0, castedReadPacket.getTiming().getNumberOfCheckpoints());
        assertEquals("FE1R", castedReadPacket.getTrack());
        assertEquals(1, castedReadPacket.getWeather());
        assertEquals(Wind.STRONG, castedReadPacket.getWind());
        AssertionUtils.assertFlagsEqual(
                RaceFlag.class,
                Set.of(RaceFlag.CAN_VOTE, RaceFlag.MID_RACE, RaceFlag.FCV),
                castedReadPacket.getFlags()
        );
        assertEquals(1000, castedReadPacket.getNumNodes());
        assertEquals(0, castedReadPacket.getFinish());
        assertEquals(224, castedReadPacket.getSplit1());
        assertEquals(589, castedReadPacket.getSplit2());
        assertEquals(701, castedReadPacket.getSplit3());
    }

    @Test
    void requestRstPacket() throws IOException {
        var inSimConnectionMock = new MockedInSimConnection();

        RstPacket.request(inSimConnectionMock).listen(((inSimConnection, packet) -> {}));

        var expectedRequestPacketBytes = new byte[] { 1, 3, 0, 19 };
        AssertionUtils.assertRequestPacketBytesEqual(expectedRequestPacketBytes, inSimConnectionMock.assertAndGetSentPacketBytes());
    }
}
