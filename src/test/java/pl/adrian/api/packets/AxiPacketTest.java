package pl.adrian.api.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.util.PacketReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.adrian.testutil.AssertionUtils.assertPacketHeaderEquals;

class AxiPacketTest {
    @Test
    void readAxiPacket() {
        var headerBytes = new byte[] { 10, 43, -112 };
        var dataBytes = new byte[] {
                0, -74, 22, -84, 5, 116, 101, 115, 116, 95, 108, 97, 121, 111, 117, 116,
                95, 110, 97, 109, 101, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(37, PacketType.AXI, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof AxiPacket);

        var castedReadPacket = (AxiPacket) readPacket;

        assertPacketHeaderEquals(40, PacketType.AXI, 144, castedReadPacket);
        assertEquals(182, castedReadPacket.getAXStart());
        assertEquals(22, castedReadPacket.getNumCP());
        assertEquals(1452, castedReadPacket.getNumO());
        assertEquals("test_layout_name", castedReadPacket.getLName());
    }
}
