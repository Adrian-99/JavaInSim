package pl.adrian.api.insim.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.internal.insim.packets.util.PacketReader;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.adrian.testutil.AssertionUtils.assertPacketHeaderEquals;

class MalPacketTest {
    @Test
    void createMalPacket() {
        var packet = new MalPacket(List.of("31A475", "DB2790", "2C6037", "A03FE5"));
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                6, 65, 0, 4, 0, 0, 0, 0, 117, -92, 49, 0, -112, 39, -37, 0,
                55, 96, 44, 0, -27, 63, -96, 0
        };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void readMalPacket() {
        var headerBytes = new byte[] { 5, 65, -112 };
        var dataBytes = new byte[] { 3, 28, 0, 0, 0, 46, 71, 61, 0, 54, -84, 87, 0, -72, 65, -97, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(17, PacketType.MAL, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof MalPacket);

        var castedReadPacket = (MalPacket) readPacket;

        assertPacketHeaderEquals(20, PacketType.MAL, 144, castedReadPacket);
        assertEquals(3, castedReadPacket.getNumM());
        assertEquals(28, castedReadPacket.getUcid());
        assertEquals(3, castedReadPacket.getSkinId().size());
        assertEquals("3D472E", castedReadPacket.getSkinId().get(0));
        assertEquals("57AC36", castedReadPacket.getSkinId().get(1));
        assertEquals("9F41B8", castedReadPacket.getSkinId().get(2));
    }
}