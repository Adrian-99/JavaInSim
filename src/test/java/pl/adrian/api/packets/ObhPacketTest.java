package pl.adrian.api.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.packets.enums.ObjectType;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.flags.ObhFlag;
import pl.adrian.internal.packets.util.PacketReader;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.adrian.testutil.AssertionUtils.assertFlagsEqual;
import static pl.adrian.testutil.AssertionUtils.assertPacketHeaderEquals;

class ObhPacketTest {
    @Test
    void readObhPacket() {
        var headerBytes = new byte[] { 6, 51, 0 };
        var dataBytes = new byte[] {
                25, 59, 1, -85, 30, 96, 90, 32, 5, -96, -24, 100, 41, -103, -24, 94,
                41, 4, 0, 20, 11
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(21, PacketType.OBH, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof ObhPacket);

        var castedReadPacket = (ObhPacket) readPacket;

        assertPacketHeaderEquals(24, PacketType.OBH, 0, castedReadPacket);
        assertEquals(25, castedReadPacket.getPlid());
        assertEquals(315, castedReadPacket.getClose());
        assertEquals(7851, castedReadPacket.getTime());
        assertEquals(96, castedReadPacket.getC().getDirection());
        assertEquals(90, castedReadPacket.getC().getHeading());
        assertEquals(32, castedReadPacket.getC().getSpeed());
        assertEquals(5, castedReadPacket.getC().getZByte());
        assertEquals(-5984, castedReadPacket.getC().getX());
        assertEquals(10596, castedReadPacket.getC().getY());
        assertEquals(-5991, castedReadPacket.getX());
        assertEquals(10590, castedReadPacket.getY());
        assertEquals(4, castedReadPacket.getZByte());
        assertEquals(ObjectType.CONE_RED, castedReadPacket.getIndex());
        assertFlagsEqual(
                ObhFlag.class,
                Set.of(ObhFlag.LAYOUT, ObhFlag.CAN_MOVE, ObhFlag.ON_SPOT),
                castedReadPacket.getObhFlags()
        );
    }
}
