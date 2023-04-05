package pl.adrian.api.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.util.PacketReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.adrian.testutil.AssertionUtils.assertPacketHeaderEquals;

class PllPacketTest {
    @Test
    void readPllPacket() {
        var headerBytes = new byte[] { 1, 23, 0 };
        var dataBytes = new byte[] { 26 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(1, PacketType.PLL, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof PllPacket);

        var castedReadPacket = (PllPacket) readPacket;

        assertPacketHeaderEquals(4, PacketType.PLL, 0, castedReadPacket);
        assertEquals(26, castedReadPacket.getPlid());
    }
}
