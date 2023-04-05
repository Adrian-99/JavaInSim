package pl.adrian.api.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.enums.PenaltyValue;
import pl.adrian.internal.packets.util.PacketReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.adrian.testutil.AssertionUtils.assertPacketHeaderEquals;

class SpxPacketTest {
    @Test
    void readSpxPacket() {
        var headerBytes = new byte[] { 4, 25, 0 };
        var dataBytes = new byte[] {
                29, 78, 102, 0, 0, 16, 37, 2, 0, 1, 0, 2, 86
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(13, PacketType.SPX, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof SpxPacket);

        var castedReadPacket = (SpxPacket) readPacket;

        assertPacketHeaderEquals(16, PacketType.SPX, 0, castedReadPacket);
        assertEquals(29, castedReadPacket.getPlid());
        assertEquals(26190, castedReadPacket.getSTime());
        assertEquals(140560, castedReadPacket.getETime());
        assertEquals(1, castedReadPacket.getSplit());
        assertEquals(PenaltyValue.NONE, castedReadPacket.getPenalty());
        assertEquals(2, castedReadPacket.getNumStops());
        assertEquals(86, castedReadPacket.getFuel200());
    }
}
