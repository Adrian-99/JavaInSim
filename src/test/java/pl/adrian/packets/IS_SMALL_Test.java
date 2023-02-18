package pl.adrian.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.packets.enums.PacketType;
import pl.adrian.packets.enums.SmallSubtype;
import pl.adrian.packets.util.PacketReader;

import static org.junit.jupiter.api.Assertions.*;

class IS_SMALL_Test {

    @Test
    void create_IS_SMALL() {
        var packet = new IS_SMALL(150, SmallSubtype.SMALL_NONE, 3885174239L);
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                2, 4, -106, 0, -33, 13, -109, -25
        };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void read_IS_SMALL() {
        var headerBytes = new byte[] { 2, 4, -112 };
        var dataBytes = new byte[] { 0, -26, -107, 96, -39 };
        var packetReader = new PacketReader(headerBytes);

        assertEquals(PacketType.ISP_SMALL, packetReader.getPacketType());
        assertEquals(5, packetReader.getDataBytesCount());

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof IS_SMALL);

        var castedReadPacket = (IS_SMALL) readPacket;

        assertEquals(8, castedReadPacket.getSize());
        assertEquals(PacketType.ISP_SMALL, castedReadPacket.getType());
        assertEquals(144, castedReadPacket.getReqI());
        assertEquals(SmallSubtype.SMALL_NONE, castedReadPacket.getSubT());
        assertEquals(3646985702L, castedReadPacket.getUVal());
    }
}
