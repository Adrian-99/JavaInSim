package pl.adrian.api.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.packets.enums.Hlvc;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.util.PacketReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.adrian.testutil.AssertionUtils.assertPacketHeaderEquals;

class HlvPacketTest {
    @Test
    void readHlvPacket() {
        var headerBytes = new byte[] { 4, 52, 0 };
        var dataBytes = new byte[] {
                38, 4, 0, -87, 26, 102, 97, 23, 15, -109, -128, -16, -22
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(13, PacketType.HLV, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof HlvPacket);

        var castedReadPacket = (HlvPacket) readPacket;

        assertPacketHeaderEquals(16, PacketType.HLV, 0, castedReadPacket);
        assertEquals(38, castedReadPacket.getPlid());
        assertEquals(Hlvc.SPEEDING, castedReadPacket.getHlvc());
        assertEquals(6825, castedReadPacket.getTime());
        assertEquals(102, castedReadPacket.getC().getDirection());
        assertEquals(97, castedReadPacket.getC().getHeading());
        assertEquals(23, castedReadPacket.getC().getSpeed());
        assertEquals(15, castedReadPacket.getC().getZByte());
        assertEquals(-32621, castedReadPacket.getC().getX());
        assertEquals(-5392, castedReadPacket.getC().getY());
    }
}
