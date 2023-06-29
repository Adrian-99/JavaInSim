package pl.adrian.api.insim.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.api.insim.packets.enums.VoteAction;
import pl.adrian.internal.insim.packets.util.PacketReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.adrian.testutil.AssertionUtils.assertPacketHeaderEquals;

class VtnPacketTest {
    @Test
    void readVtnPacket() {
        var headerBytes = new byte[] { 2, 16, 0 };
        var dataBytes = new byte[] { 0, 34, 2, 0, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(5, PacketType.VTN, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof VtnPacket);

        var castedReadPacket = (VtnPacket) readPacket;

        assertPacketHeaderEquals(8, PacketType.VTN, 0, castedReadPacket);
        assertEquals(34, castedReadPacket.getUcid());
        assertEquals(VoteAction.RESTART, castedReadPacket.getAction());
    }
}
