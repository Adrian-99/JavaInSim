package pl.adrian.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.packets.enums.PacketType;
import pl.adrian.packets.enums.Product;
import pl.adrian.packets.util.PacketReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IS_VER_Test {

    @Test
    void readPacket() {
        var headerBytes = new byte[] { 5, 2, -112 };
        var dataBytes = new byte[] { 0, 48, 46, 55, 68, 0, 0, 0, 0, 83, 51, 0, 0, 0, 0, 9 };
        var packetReader = new PacketReader(headerBytes);

        assertEquals(PacketType.ISP_VER, packetReader.getPacketType());
        assertEquals(17, packetReader.getDataBytesCount());

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof IS_VER);

        var castedReadPacket = (IS_VER) readPacket;

        assertEquals(20, castedReadPacket.getSize());
        assertEquals(PacketType.ISP_VER, castedReadPacket.getType());
        assertEquals(144, castedReadPacket.getReqI());
        assertEquals("0.7D", castedReadPacket.getVersion());
        assertEquals(Product.S3, castedReadPacket.getProduct());
        assertEquals(9, castedReadPacket.getInSimVer());
    }
}
