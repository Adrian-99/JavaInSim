package pl.adrian.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.packets.enums.PacketType;
import pl.adrian.packets.enums.TinySubtype;
import pl.adrian.packets.util.PacketReader;

import static org.junit.jupiter.api.Assertions.*;

class IS_TINY_Test {

    @Test
    void create_IS_TINY() {
        var packet = new IS_TINY(150, TinySubtype.TINY_NONE);
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                1, 3, -106, 0
        };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void read_IS_TINY() {
        var headerBytes = new byte[] { 2, 3, -112 };
        var dataBytes = new byte[] { 0 };
        var packetReader = new PacketReader(headerBytes);

        assertEquals(PacketType.ISP_TINY, packetReader.getPacketType());
        assertEquals(1, packetReader.getDataBytesCount());

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof IS_TINY);

        var castedReadPacket = (IS_TINY) readPacket;

        assertEquals(4, castedReadPacket.getSize());
        assertEquals(PacketType.ISP_TINY, castedReadPacket.getType());
        assertEquals(144, castedReadPacket.getReqI());
        assertEquals(TinySubtype.TINY_NONE, castedReadPacket.getSubT());
    }
}
