package pl.adrian.api.insim.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.common.flags.Flags;
import pl.adrian.api.common.structures.Vec;
import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.api.insim.packets.enums.ViewIdentifier;
import pl.adrian.api.insim.packets.flags.CppFlag;
import pl.adrian.internal.insim.packets.util.PacketReader;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static pl.adrian.testutil.AssertionUtils.assertFlagsEqual;
import static pl.adrian.testutil.AssertionUtils.assertPacketHeaderEquals;

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
        assertEquals(-2589854, castedReadPacket.getPos().getX());
        assertEquals(7984562, castedReadPacket.getPos().getY());
        assertEquals(83542, castedReadPacket.getPos().getZ());
        assertEquals(15642, castedReadPacket.getH());
        assertEquals(325, castedReadPacket.getP());
        assertEquals(55, castedReadPacket.getR());
        assertEquals(36, castedReadPacket.getViewPlid());
        assertEquals(ViewIdentifier.CAM, castedReadPacket.getInGameCam());
        assertEquals(95.5, castedReadPacket.getFov());
        assertEquals(2770, castedReadPacket.getTime());
        assertFlagsEqual(CppFlag.class, Set.of(CppFlag.SHIFTU), castedReadPacket.getFlags());
    }
}
