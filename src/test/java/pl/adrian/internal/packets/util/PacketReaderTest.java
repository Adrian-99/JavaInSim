package pl.adrian.internal.packets.util;

import org.junit.jupiter.api.Test;
import pl.adrian.api.packets.SmallPacket;
import pl.adrian.api.packets.TinyPacket;
import pl.adrian.api.packets.VerPacket;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.enums.Product;
import pl.adrian.api.packets.enums.SmallSubtype;
import pl.adrian.api.packets.enums.TinySubtype;
import pl.adrian.internal.packets.exceptions.PacketReadingException;

import static org.junit.jupiter.api.Assertions.*;

class PacketReaderTest {

    @Test
    void packetReader_tooShortHeader() {
        var headerBytes = new byte[] { 1, 1 };
        assertThrows(PacketReadingException.class, () -> new PacketReader(headerBytes));
    }

    @Test
    void packetReader_tooLongHeader() {
        var headerBytes = new byte[] { 1, 1, 1, 1 };
        assertThrows(PacketReadingException.class, () -> new PacketReader(headerBytes));
    }

    @Test
    void read_forUnrecognizedReadablePacketType() {
        var headerBytes = new byte[] { 1, 0, 0 };
        var dataBytes = new byte[] { 0 };
        var packetBuilder = new PacketReader(headerBytes);

        assertEquals(PacketType.NONE, packetBuilder.getPacketType());
        assertEquals(1, packetBuilder.getDataBytesCount());
        assertThrows(PacketReadingException.class, () -> packetBuilder.read(dataBytes));
    }

    @Test
    void readVerPacket() {
        var headerBytes = new byte[] { 5, 2, -112 };
        var dataBytes = new byte[] { 0, 48, 46, 55, 68, 0, 0, 0, 0, 83, 51, 0, 0, 0, 0, 9 };
        var packetReader = new PacketReader(headerBytes);

        assertEquals(PacketType.VER, packetReader.getPacketType());
        assertEquals(17, packetReader.getDataBytesCount());

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof VerPacket);

        var castedReadPacket = (VerPacket) readPacket;

        assertEquals(20, castedReadPacket.getSize());
        assertEquals(PacketType.VER, castedReadPacket.getType());
        assertEquals(144, castedReadPacket.getReqI());
        assertEquals("0.7D", castedReadPacket.getVersion());
        assertEquals(Product.S3, castedReadPacket.getProduct());
        assertEquals(9, castedReadPacket.getInSimVer());
    }

    @Test
    void readTinyPacket() {
        var headerBytes = new byte[] { 1, 3, -112 };
        var dataBytes = new byte[] { 0 };
        var packetReader = new PacketReader(headerBytes);

        assertEquals(PacketType.TINY, packetReader.getPacketType());
        assertEquals(1, packetReader.getDataBytesCount());

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof TinyPacket);

        var castedReadPacket = (TinyPacket) readPacket;

        assertEquals(4, castedReadPacket.getSize());
        assertEquals(PacketType.TINY, castedReadPacket.getType());
        assertEquals(144, castedReadPacket.getReqI());
        assertEquals(TinySubtype.NONE, castedReadPacket.getSubT());
    }

    @Test
    void readSmallPacket() {
        var headerBytes = new byte[] { 2, 4, -112 };
        var dataBytes = new byte[] { 0, -26, -107, 96, -39 };
        var packetReader = new PacketReader(headerBytes);

        assertEquals(PacketType.SMALL, packetReader.getPacketType());
        assertEquals(5, packetReader.getDataBytesCount());

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof SmallPacket);

        var castedReadPacket = (SmallPacket) readPacket;

        assertEquals(8, castedReadPacket.getSize());
        assertEquals(PacketType.SMALL, castedReadPacket.getType());
        assertEquals(144, castedReadPacket.getReqI());
        assertEquals(SmallSubtype.NONE, castedReadPacket.getSubT());
        assertEquals(3646985702L, castedReadPacket.getUVal());
    }
}
