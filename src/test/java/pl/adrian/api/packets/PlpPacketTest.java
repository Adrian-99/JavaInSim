package pl.adrian.api.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.util.PacketReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.adrian.testutil.AssertionUtils.assertPacketHeaderEquals;

class PlpPacketTest {
    @Test
    void readPlpPacket() {
        var headerBytes = new byte[] { 1, 22, 0 };
        var dataBytes = new byte[] { 31 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(1, PacketType.PLP, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof PlpPacket);

        var castedReadPacket = (PlpPacket) readPacket;

        assertPacketHeaderEquals(4, PacketType.PLP, 0, castedReadPacket);
        assertEquals(31, castedReadPacket.getPlid());
    }
}
