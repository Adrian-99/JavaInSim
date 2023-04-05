package pl.adrian.api.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.util.PacketReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.adrian.testutil.AssertionUtils.assertPacketHeaderEquals;

class IiiPacketTest {
    @Test
    void readIiiPacket() {
        var headerBytes = new byte[] { 4, 12, 0 };
        var dataBytes = new byte[] { 0, 23, 17, 0, 0, 116, 101, 115, 116, 0, 0, 0, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(13, PacketType.III, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof IiiPacket);

        var castedReadPacket = (IiiPacket) readPacket;

        assertPacketHeaderEquals(16, PacketType.III, 0, castedReadPacket);
        assertEquals(23, castedReadPacket.getUcid());
        assertEquals(17, castedReadPacket.getPlid());
        assertEquals("test", castedReadPacket.getMsg());
    }
}
