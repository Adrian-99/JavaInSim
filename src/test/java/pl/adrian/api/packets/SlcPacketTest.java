package pl.adrian.api.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.util.PacketReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.adrian.testutil.AssertionUtils.assertCarEquals;
import static pl.adrian.testutil.AssertionUtils.assertPacketHeaderEquals;

class SlcPacketTest {
    @Test
    void readSlcPacket() {
        var headerBytes = new byte[] { 2, 62, -112 };
        var dataBytes = new byte[] { 9, -63, 104, 74, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(5, PacketType.SLC, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof SlcPacket);

        var castedReadPacket = (SlcPacket) readPacket;

        assertPacketHeaderEquals(8, PacketType.SLC, 144, castedReadPacket);
        assertEquals(9, castedReadPacket.getUcid());
        assertCarEquals("4A68C1", castedReadPacket.getCar());
    }
}
