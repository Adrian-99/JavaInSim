package pl.adrian.api.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.packets.enums.LeaveReason;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.util.PacketReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.adrian.testutil.AssertionUtils.assertPacketHeaderEquals;

class CnlPacketTest {
    @Test
    void readCnlPacket() {
        var headerBytes = new byte[] { 2, 19, 0 };
        var dataBytes = new byte[] { 38, 3, 26, 0, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(5, PacketType.CNL, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof CnlPacket);

        var castedReadPacket = (CnlPacket) readPacket;

        assertPacketHeaderEquals(8, PacketType.CNL, 0, castedReadPacket);
        assertEquals(38, castedReadPacket.getUcid());
        assertEquals(LeaveReason.KICKED, castedReadPacket.getReason());
        assertEquals(26, castedReadPacket.getTotal());
    }
}
