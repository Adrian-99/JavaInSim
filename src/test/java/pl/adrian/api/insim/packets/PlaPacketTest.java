package pl.adrian.api.insim.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.api.insim.packets.enums.PitLaneFact;
import pl.adrian.internal.insim.packets.util.PacketReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.adrian.testutil.AssertionUtils.assertPacketHeaderEquals;

class PlaPacketTest {
    @Test
    void readPlaPacket() {
        var headerBytes = new byte[] { 2, 28, 0 };
        var dataBytes = new byte[] { 19, 2, 0, 0, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(5, PacketType.PLA, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof PlaPacket);

        var castedReadPacket = (PlaPacket) readPacket;

        assertPacketHeaderEquals(8, PacketType.PLA, 0, castedReadPacket);
        assertEquals(19, castedReadPacket.getPlid());
        assertEquals(PitLaneFact.NO_PURPOSE, castedReadPacket.getFact());
    }
}
