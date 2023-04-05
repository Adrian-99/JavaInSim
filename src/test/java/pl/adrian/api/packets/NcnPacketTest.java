package pl.adrian.api.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.flags.NcnFlag;
import pl.adrian.internal.packets.util.PacketReader;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.adrian.testutil.AssertionUtils.assertFlagsEqual;
import static pl.adrian.testutil.AssertionUtils.assertPacketHeaderEquals;

class NcnPacketTest {
    @Test
    void readNcnPacket() {
        var headerBytes = new byte[] { 14, 18, -112 };
        var dataBytes = new byte[] {
                21, 116, 104, 101, 117, 115, 101, 114, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 78, 101, 119, 32, 85, 115, 101,
                114, 32, 78, 105, 99, 107, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 1, 34, 4, 0
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(53, PacketType.NCN, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof NcnPacket);

        var castedReadPacket = (NcnPacket) readPacket;

        assertPacketHeaderEquals(56, PacketType.NCN, 144, castedReadPacket);
        assertEquals(21, castedReadPacket.getUcid());
        assertEquals("theuser", castedReadPacket.getUName());
        assertEquals("New User Nick", castedReadPacket.getPName());
        assertTrue(castedReadPacket.isAdmin());
        assertEquals(34, castedReadPacket.getTotal());
        assertFlagsEqual(NcnFlag.class, Set.of(NcnFlag.REMOTE), castedReadPacket.getFlags());
    }
}
