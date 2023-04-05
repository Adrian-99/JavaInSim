package pl.adrian.api.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.util.PacketReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.adrian.testutil.AssertionUtils.assertPacketHeaderEquals;

class TocPacketTest {
    @Test
    void readTocPacket() {
        var headerBytes = new byte[] { 2, 31, 0 };
        var dataBytes = new byte[] { 7, 25, 31, 0, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(5, PacketType.TOC, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof TocPacket);

        var castedReadPacket = (TocPacket) readPacket;

        assertPacketHeaderEquals(8, PacketType.TOC, 0, castedReadPacket);
        assertEquals(7, castedReadPacket.getPlid());
        assertEquals(25, castedReadPacket.getOldUcid());
        assertEquals(31, castedReadPacket.getNewUcid());
    }
}
