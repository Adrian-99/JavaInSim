/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.insim.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.insim.packets.enums.*;
import pl.adrian.internal.insim.packets.util.PacketReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.adrian.testutil.AssertionUtils.assertPacketHeaderEquals;

class CimPacketTest {
    @Test
    void readCimPacket_withNormalMode() {
        var headerBytes = new byte[] { 2, 64, 0 };
        var dataBytes = new byte[] { 12, 0, 2, 0, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(5, PacketType.CIM, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof CimPacket);

        var castedReadPacket = (CimPacket) readPacket;

        assertPacketHeaderEquals(8, PacketType.CIM, 0, castedReadPacket);
        assertEquals(12, castedReadPacket.getUcid());
        assertEquals(InterfaceMode.NORMAL, castedReadPacket.getMode());
        assertTrue(castedReadPacket.getNormalSubmode().isPresent());
        assertEquals(NormalInterfaceSubmode.WHEEL_DAMAGE, castedReadPacket.getNormalSubmode().get());
        assertTrue(castedReadPacket.getGarageSubmode().isEmpty());
        assertTrue(castedReadPacket.getShiftUSubmode().isEmpty());
        assertEquals(ObjectType.NULL, castedReadPacket.getSelType());
    }

    @Test
    void readCimPacket_withGarageMode() {
        var headerBytes = new byte[] { 2, 64, 0 };
        var dataBytes = new byte[] { 12, 3, 5, 0, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(5, PacketType.CIM, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof CimPacket);

        var castedReadPacket = (CimPacket) readPacket;

        assertPacketHeaderEquals(8, PacketType.CIM, 0, castedReadPacket);
        assertEquals(12, castedReadPacket.getUcid());
        assertEquals(InterfaceMode.GARAGE, castedReadPacket.getMode());
        assertTrue(castedReadPacket.getNormalSubmode().isEmpty());
        assertTrue(castedReadPacket.getGarageSubmode().isPresent());
        assertEquals(GarageInterfaceSubmode.DRIVE, castedReadPacket.getGarageSubmode().get());
        assertTrue(castedReadPacket.getShiftUSubmode().isEmpty());
        assertEquals(ObjectType.NULL, castedReadPacket.getSelType());
    }

    @Test
    void readCimPacket_withShiftUMode() {
        var headerBytes = new byte[] { 2, 64, 0 };
        var dataBytes = new byte[] { 12, 6, 2, -118, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(5, PacketType.CIM, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof CimPacket);

        var castedReadPacket = (CimPacket) readPacket;

        assertPacketHeaderEquals(8, PacketType.CIM, 0, castedReadPacket);
        assertEquals(12, castedReadPacket.getUcid());
        assertEquals(InterfaceMode.SHIFTU, castedReadPacket.getMode());
        assertTrue(castedReadPacket.getNormalSubmode().isEmpty());
        assertTrue(castedReadPacket.getGarageSubmode().isEmpty());
        assertTrue(castedReadPacket.getShiftUSubmode().isPresent());
        assertEquals(ShiftUInterfaceSubmode.EDIT, castedReadPacket.getShiftUSubmode().get());
        assertEquals(ObjectType.POST_RED, castedReadPacket.getSelType());
    }

    @Test
    void readCimPacket_withOtherMode() {
        var headerBytes = new byte[] { 2, 64, 0 };
        var dataBytes = new byte[] { 12, 4, 0, 1, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(5, PacketType.CIM, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof CimPacket);

        var castedReadPacket = (CimPacket) readPacket;

        assertPacketHeaderEquals(8, PacketType.CIM, 0, castedReadPacket);
        assertEquals(12, castedReadPacket.getUcid());
        assertEquals(InterfaceMode.CAR_SELECT, castedReadPacket.getMode());
        assertTrue(castedReadPacket.getNormalSubmode().isEmpty());
        assertTrue(castedReadPacket.getGarageSubmode().isEmpty());
        assertTrue(castedReadPacket.getShiftUSubmode().isEmpty());
        assertEquals(ObjectType.NULL, castedReadPacket.getSelType());
    }
}
