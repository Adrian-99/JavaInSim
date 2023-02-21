package pl.adrian.api.packets.readable;

import org.junit.jupiter.api.Test;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.enums.TinySubtype;
import pl.adrian.internal.packets.util.PacketReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TinyPacketTest {

    @Test
    void readTinyPacket() {
        var headerBytes = new byte[] { 2, 3, -112 };
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
}
