package pl.adrian.api.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.util.PacketReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.adrian.testutil.AssertionUtils.assertPacketHeaderEquals;

class PsfPacketTest {
    @Test
    void readPsfPacket() {
        var headerBytes = new byte[] { 3, 27, 0 };
        var dataBytes = new byte[] {
                23, -96, 23, 2, 0, 0, 0, 0, 0
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(9, PacketType.PSF, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof PsfPacket);

        var castedReadPacket = (PsfPacket) readPacket;

        assertPacketHeaderEquals(12, PacketType.PSF, 0, castedReadPacket);
        assertEquals(23, castedReadPacket.getPlid());
        assertEquals(137120, castedReadPacket.getSTime());
    }
}
