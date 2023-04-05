package pl.adrian.api.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.util.PacketReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.adrian.testutil.AssertionUtils.assertPacketHeaderEquals;

class CrsPacketTest {
    @Test
    void readCrsPacket() {
        var headerBytes = new byte[] { 1, 41, 0 };
        var dataBytes = new byte[] { 5 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(1, PacketType.CRS, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof CrsPacket);

        var castedReadPacket = (CrsPacket) readPacket;

        assertPacketHeaderEquals(4, PacketType.CRS, 0, castedReadPacket);
        assertEquals(5, castedReadPacket.getPlid());
    }
}
