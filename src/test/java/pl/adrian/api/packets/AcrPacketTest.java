package pl.adrian.api.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.packets.enums.AcrResult;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.util.PacketReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.adrian.testutil.AssertionUtils.assertPacketHeaderEquals;

class AcrPacketTest {
    @Test
    void readAcrPacket() {
        var headerBytes = new byte[] { 5, 55, 0 };
        var dataBytes = new byte[] { 0, 31, 1, 3, 0, 47, 99, 111, 109, 109, 97, 110, 100, 0, 0, 0, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(17, PacketType.ACR, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof AcrPacket);

        var castedReadPacket = (AcrPacket) readPacket;

        assertPacketHeaderEquals(20, PacketType.ACR, 0, castedReadPacket);
        assertEquals(31, castedReadPacket.getUcid());
        assertTrue(castedReadPacket.isAdmin());
        assertEquals(AcrResult.UNKNOWN_COMMAND, castedReadPacket.getResult());
        assertEquals("/command", castedReadPacket.getText());
    }
}
