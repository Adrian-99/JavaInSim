package pl.adrian.api.insim.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.insim.packets.enums.DefaultCar;
import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.api.insim.packets.enums.TyreCompound;
import pl.adrian.api.insim.packets.flags.PassengerFlag;
import pl.adrian.api.insim.packets.flags.PlayerFlag;
import pl.adrian.api.insim.packets.flags.PlayerTypeFlag;
import pl.adrian.api.insim.packets.flags.SetupFlag;
import pl.adrian.internal.insim.packets.util.PacketReader;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.adrian.testutil.AssertionUtils.*;
import static pl.adrian.testutil.AssertionUtils.assertFlagsEqual;

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

        assertTrue(readPacket instanceof NplPacket);

        var castedReadPacket = (NplPacket) readPacket;

        assertPacketHeaderEquals(76, PacketType.NPL, 144, castedReadPacket);
        assertEquals(57, castedReadPacket.getPlid());
        assertEquals(23, castedReadPacket.getUcid());
        assertFlagsEqual(PlayerTypeFlag.class, Set.of(PlayerTypeFlag.AI, PlayerTypeFlag.REMOTE), castedReadPacket.getPType());
        assertFlagsEqual(
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
        assertFlagsEqual(
                PassengerFlag.class,
                Set.of(PassengerFlag.REAR_LEFT_MALE, PassengerFlag.REAR_MIDDLE_FEMALE, PassengerFlag.REAR_RIGHT_MALE),
                castedReadPacket.getPass()
        );
        assertEquals(0, castedReadPacket.getRWAdj());
        assertEquals(0, castedReadPacket.getFWAdj());
        assertFlagsEqual(
                SetupFlag.class,
                Set.of(SetupFlag.SYMM_WHEELS, SetupFlag.TC_ENABLE, SetupFlag.ABS_ENABLE),
                castedReadPacket.getSetF()
        );
        assertEquals(3, castedReadPacket.getNumP());
        assertEquals(1, castedReadPacket.getConfig());
        assertEquals(54, castedReadPacket.getFuel());
    }
}
