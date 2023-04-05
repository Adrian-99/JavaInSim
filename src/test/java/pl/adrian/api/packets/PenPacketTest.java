package pl.adrian.api.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.enums.PenaltyReason;
import pl.adrian.api.packets.enums.PenaltyValue;
import pl.adrian.internal.packets.util.PacketReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.adrian.testutil.AssertionUtils.assertPacketHeaderEquals;

class PenPacketTest {
    @Test
    void readPenPacket() {
        var headerBytes = new byte[] { 2, 30, 0 };
        var dataBytes = new byte[] { 34, 0, 6, 3, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(5, PacketType.PEN, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof PenPacket);

        var castedReadPacket = (PenPacket) readPacket;

        assertPacketHeaderEquals(8, PacketType.PEN, 0, castedReadPacket);
        assertEquals(34, castedReadPacket.getPlid());
        assertEquals(PenaltyValue.NONE, castedReadPacket.getOldPen());
        assertEquals(PenaltyValue.PLUS_45_S, castedReadPacket.getNewPen());
        assertEquals(PenaltyReason.FALSE_START, castedReadPacket.getReason());
    }
}
