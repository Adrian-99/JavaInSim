package pl.adrian.api.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.flags.CompCarFlag;
import pl.adrian.internal.packets.util.PacketReader;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.adrian.testutil.AssertionUtils.assertFlagsEqual;
import static pl.adrian.testutil.AssertionUtils.assertPacketHeaderEquals;

class ConPacketTest {
    @Test
    @SuppressWarnings("java:S5961")
    void readConPacket() {
        var headerBytes = new byte[] { 10, 50, 0 };
        var dataBytes = new byte[] {
                0, -93, 2, -56, 72, 5, 32, 0, -16, 45, -16, 64, 36, 5, 8, -8,
                1, 52, 49, -98, -101, 12, 0, 0, 24, 13, -1, 48, 32, -7, -20, -9,
                3, 43, 49, -103, -101
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(37, PacketType.CON, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof ConPacket);

        var castedReadPacket = (ConPacket) readPacket;

        assertPacketHeaderEquals(40, PacketType.CON, 0, castedReadPacket);
        assertEquals(675, castedReadPacket.getClose());
        assertEquals(18632, castedReadPacket.getTime());
        assertEquals(5, castedReadPacket.getA().getPlid());
        assertFlagsEqual(CompCarFlag.class, Set.of(CompCarFlag.LAG), castedReadPacket.getA().getInfo());
        assertEquals(-16, castedReadPacket.getA().getSteer());
        assertEquals(2, castedReadPacket.getA().getThr());
        assertEquals(13, castedReadPacket.getA().getBrk());
        assertEquals(15, castedReadPacket.getA().getClu());
        assertEquals(0, castedReadPacket.getA().getHan());
        assertEquals(4, castedReadPacket.getA().getGear());
        assertEquals(36, castedReadPacket.getA().getSpeed());
        assertEquals(5, castedReadPacket.getA().getDirection());
        assertEquals(8, castedReadPacket.getA().getHeading());
        assertEquals(-8, castedReadPacket.getA().getAccelF());
        assertEquals(1, castedReadPacket.getA().getAccelR());
        assertEquals(12596, castedReadPacket.getA().getX());
        assertEquals(-25698, castedReadPacket.getA().getY());
        assertEquals(12, castedReadPacket.getB().getPlid());
        assertFlagsEqual(CompCarFlag.class, Set.of(), castedReadPacket.getB().getInfo());
        assertEquals(24, castedReadPacket.getB().getSteer());
        assertEquals(0, castedReadPacket.getB().getThr());
        assertEquals(13, castedReadPacket.getB().getBrk());
        assertEquals(15, castedReadPacket.getB().getClu());
        assertEquals(15, castedReadPacket.getB().getHan());
        assertEquals(3, castedReadPacket.getB().getGear());
        assertEquals(32, castedReadPacket.getB().getSpeed());
        assertEquals(249, castedReadPacket.getB().getDirection());
        assertEquals(236, castedReadPacket.getB().getHeading());
        assertEquals(-9, castedReadPacket.getB().getAccelF());
        assertEquals(3, castedReadPacket.getB().getAccelR());
        assertEquals(12587, castedReadPacket.getB().getX());
        assertEquals(-25703, castedReadPacket.getB().getY());
    }
}
