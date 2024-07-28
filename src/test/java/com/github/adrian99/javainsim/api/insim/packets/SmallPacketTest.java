/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.api.common.enums.DefaultCar;
import com.github.adrian99.javainsim.api.insim.packets.flags.LclFlag;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketReader;
import com.github.adrian99.javainsim.testutil.AssertionUtils;
import com.github.adrian99.javainsim.testutil.MockedInSimConnection;
import org.junit.jupiter.api.Test;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.insim.packets.enums.SmallSubtype;
import com.github.adrian99.javainsim.api.insim.packets.enums.VoteAction;
import com.github.adrian99.javainsim.api.insim.packets.flags.LcsFlag;

import java.io.IOException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static com.github.adrian99.javainsim.testutil.AssertionUtils.*;

class SmallPacketTest {
    @Test
    void createSmallPacket() {
        var packet = new SmallPacket(SmallSubtype.NONE, 3885174239L);
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                2, 4, 0, 0, -33, 13, -109, -25
        };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    @SuppressWarnings("deprecation")
    void createSmallPacket_withLcsFlags() {
        var packet = new SmallPacket(LcsFlag.SIGNALS_LEFT, LcsFlag.HEADLIGHTS_OFF, LcsFlag.HORN_1);
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                2, 4, 0, 9, 13, 1, 1, 0
        };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void createSmallPacket_withLclFlags() {
        var packet = new SmallPacket(LclFlag.SIGNALS_RIGHT, LclFlag.LIGHTS_LOW, LclFlag.EXTRA_ON);
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                2, 4, 0, 10, 69, 0, 74, 0
        };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void createSmallPacket_withCars() {
        var packet = new SmallPacket(DefaultCar.FXR, DefaultCar.XRR, DefaultCar.FZR);
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                2, 4, 0, 8, 0, -128, 3, 0
        };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void createSmallPacket_withInterval() {
        var packet = new SmallPacket(2000);
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                2, 4, 0, 7, -48, 7, 0, 0
        };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void readSmallPacket() {
        var headerBytes = new byte[] { 2, 4, -112 };
        var dataBytes = new byte[] { 0, -26, -107, 96, -39 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(5, PacketType.SMALL, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertInstanceOf(SmallPacket.class, readPacket);

        var castedReadPacket = (SmallPacket) readPacket;

        assertPacketHeaderEquals(8, PacketType.SMALL, 144, castedReadPacket);
        assertEquals(SmallSubtype.NONE, castedReadPacket.getSubT());
        assertEquals(3646985702L, castedReadPacket.getUVal());
        assertTrue(castedReadPacket.getVoteAction().isEmpty());
        assertTrue(castedReadPacket.getCars().isEmpty());
    }

    @Test
    void readSmallPacket_withVtaSubtype() {
        var headerBytes = new byte[] { 2, 4, -112 };
        var dataBytes = new byte[] { 3, 3, 0, 0, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(5, PacketType.SMALL, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertInstanceOf(SmallPacket.class, readPacket);

        var castedReadPacket = (SmallPacket) readPacket;

        assertPacketHeaderEquals(8, PacketType.SMALL, 144, castedReadPacket);
        assertEquals(SmallSubtype.VTA, castedReadPacket.getSubT());
        assertEquals(3, castedReadPacket.getUVal());
        assertTrue(castedReadPacket.getVoteAction().isPresent());
        assertEquals(VoteAction.QUALIFY, castedReadPacket.getVoteAction().get());
        assertTrue(castedReadPacket.getCars().isEmpty());
    }

    @Test
    void readSmallPacket_withAlcSubtype() {
        var headerBytes = new byte[] { 2, 4, -112 };
        var dataBytes = new byte[] { 8, 0, 72, 12, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(5, PacketType.SMALL, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertInstanceOf(SmallPacket.class, readPacket);

        var castedReadPacket = (SmallPacket) readPacket;

        assertPacketHeaderEquals(8, PacketType.SMALL, 144, castedReadPacket);
        assertEquals(SmallSubtype.ALC, castedReadPacket.getSubT());
        assertEquals(804864, castedReadPacket.getUVal());
        assertTrue(castedReadPacket.getVoteAction().isEmpty());
        assertTrue(castedReadPacket.getCars().isPresent());
        AssertionUtils.assertFlagsEqual(DefaultCar.class, Set.of(DefaultCar.FOX, DefaultCar.FO8, DefaultCar.BF1, DefaultCar.FBM), castedReadPacket.getCars().get());
    }

    @Test
    void requestSmallPacketForAllowedCars() throws IOException {
        var inSimConnectionMock = new MockedInSimConnection();

        SmallPacket.request(inSimConnectionMock)
                .forAllowedCars()
                .listen(((inSimConnection, packet) -> {}));

        var expectedRequestPacketBytes = new byte[] { 1, 3, 0, 24 };
        AssertionUtils.assertRequestPacketBytesEqual(expectedRequestPacketBytes, inSimConnectionMock.assertAndGetSentPacketBytes());
    }

    @Test
    void requestSmallPacketForCurrentTime() throws IOException {
        var inSimConnectionMock = new MockedInSimConnection();

        SmallPacket.request(inSimConnectionMock)
                .forCurrentTime()
                .listen(((inSimConnection, packet) -> {}));

        var expectedRequestPacketBytes = new byte[] { 1, 3, 0, 8 };
        AssertionUtils.assertRequestPacketBytesEqual(expectedRequestPacketBytes, inSimConnectionMock.assertAndGetSentPacketBytes());
    }
}
