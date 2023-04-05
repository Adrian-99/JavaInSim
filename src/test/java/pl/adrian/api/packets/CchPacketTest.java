package pl.adrian.api.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.enums.ViewIdentifier;
import pl.adrian.internal.packets.util.PacketReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.adrian.testutil.AssertionUtils.assertPacketHeaderEquals;

class CchPacketTest {
    @Test
    void readCchPacket() {
        var headerBytes = new byte[] { 2, 29, 0 };
        var dataBytes = new byte[] { 11, 1, 0, 0, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(5, PacketType.CCH, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof CchPacket);

        var castedReadPacket = (CchPacket) readPacket;

        assertPacketHeaderEquals(8, PacketType.CCH, 0, castedReadPacket);
        assertEquals(11, castedReadPacket.getPlid());
        assertEquals(ViewIdentifier.HELI, castedReadPacket.getCamera());
    }
}
