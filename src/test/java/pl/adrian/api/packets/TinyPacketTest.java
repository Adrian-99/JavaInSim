package pl.adrian.api.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.enums.TinySubtype;
import pl.adrian.internal.packets.util.PacketReader;

import static org.junit.jupiter.api.Assertions.*;
import static pl.adrian.testutil.AssertionUtils.assertPacketHeaderEquals;

class TinyPacketTest {
    @Test
    void createTinyPacket() {
        var packet = new TinyPacket(150, TinySubtype.NONE);
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                1, 3, -106, 0
        };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void readTinyPacket() {
        var headerBytes = new byte[] { 1, 3, -112 };
        var dataBytes = new byte[] { 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(1, PacketType.TINY, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof TinyPacket);

        var castedReadPacket = (TinyPacket) readPacket;

        assertPacketHeaderEquals(4, PacketType.TINY, 144, castedReadPacket);
        assertEquals(TinySubtype.NONE, castedReadPacket.getSubT());
    }
}
