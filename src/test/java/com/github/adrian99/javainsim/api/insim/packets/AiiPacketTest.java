/*
 * Copyright (c) 2025, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.insim.packets.flags.AiiFlag;
import com.github.adrian99.javainsim.api.outgauge.flags.DashLight;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketReader;
import com.github.adrian99.javainsim.testutil.MockedInSimConnection;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Set;

import static com.github.adrian99.javainsim.testutil.AssertionUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class AiiPacketTest {
    @Test
    void readAiiPacket() {
        var headerBytes = new byte[] { 24, 69, -112 };
        var dataBytes = new byte[] {
                51, -16, 22, -56, 63, -121, 53, -107, -67, 111, -51, -113, 63, -33, 79, -119,
                66, -35, 36, -72, -64, -77, -46, 57, -64, -115, -105, -10, 63, 92, 56, -64,
                -66, -127, -107, 3, 62, 114, 121, 42, 65, 10, -41, 35, 64, -84, -59, 39,
                60, -75, -18, 1, 0, 69, -128, -2, -1, 111, 14, 0, 0, 1, 3, 0,
                0, 24, -59, -89, 69, 0, 0, 0, 0, 0, 0, 0, 0, 16, -116, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(93, PacketType.AII, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertInstanceOf(AiiPacket.class, readPacket);

        var castedReadPacket = (AiiPacket) readPacket;

        assertPacketHeaderEquals(96, PacketType.AII, 144, castedReadPacket);
        assertEquals(51, castedReadPacket.getPlid());
        assertEquals(1.5631999969482421875f, castedReadPacket.getOsData().getAngVel().getX());
        assertEquals(-0.072856001555919647216796875f, castedReadPacket.getOsData().getAngVel().getY());
        assertEquals(1.12345683574676513671875f, castedReadPacket.getOsData().getAngVel().getZ());
        assertEquals(68.65599822998047, castedReadPacket.getOsData().getHeading());
        assertEquals(-5.754499912261963, castedReadPacket.getOsData().getPitch());
        assertEquals(-2.903485059738159, castedReadPacket.getOsData().getRoll());
        assertEquals(1.9264999628067017, castedReadPacket.getOsData().getAccel().getX());
        assertEquals(-0.37542998790740967, castedReadPacket.getOsData().getAccel().getY());
        assertEquals(0.12849999964237213, castedReadPacket.getOsData().getAccel().getZ());
        assertEquals(10.65464973449707, castedReadPacket.getOsData().getVel().getX());
        assertEquals(2.559999942779541, castedReadPacket.getOsData().getVel().getY());
        assertEquals(0.010239999741315842, castedReadPacket.getOsData().getVel().getZ());
        assertEquals(126645, castedReadPacket.getOsData().getPos().getX());
        assertEquals(-98235, castedReadPacket.getOsData().getPos().getY());
        assertEquals(3695, castedReadPacket.getOsData().getPos().getZ());
        assertFlagsEqual(AiiFlag.class, Set.of(AiiFlag.IGNITION), castedReadPacket.getFlags());
        assertEquals(3, castedReadPacket.getGear());
        assertEquals(5368.63671875f, castedReadPacket.getRpm());
        assertFlagsEqual(
                DashLight.class,
                Set.of(DashLight.TC, DashLight.ABS, DashLight.ENGINE, DashLight.FUELWARN),
                castedReadPacket.getShowLights()
        );
    }

    @Test
    void requestAiiPacket() throws IOException {
        var inSimConnectionMock = new MockedInSimConnection();

        AiiPacket.request(inSimConnectionMock, 74).listen(((inSimConnection, packet) -> {}));

        var expectedRequestPacketBytes = new byte[] { 2, 4, 0, 11, 74, 0, 0, 0 };
        assertRequestPacketBytesEqual(expectedRequestPacketBytes, inSimConnectionMock.assertAndGetSentPacketBytes());
    }
}
