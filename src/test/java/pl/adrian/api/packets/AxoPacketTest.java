package pl.adrian.api.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.util.PacketReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.adrian.testutil.AssertionUtils.assertPacketHeaderEquals;

class AxoPacketTest {
    @Test
    void readAxoPacket() {
        var headerBytes = new byte[] { 1, 44, 0 };
        var dataBytes = new byte[] { 36 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(1, PacketType.AXO, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof AxoPacket);

        var castedReadPacket = (AxoPacket) readPacket;

        assertPacketHeaderEquals(4, PacketType.AXO, 0, castedReadPacket);
        assertEquals(36, castedReadPacket.getPlid());
    }
}