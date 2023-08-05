package pl.adrian.api.insim.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.api.insim.packets.flags.ButtonInstFlag;
import pl.adrian.internal.insim.packets.util.PacketReader;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.adrian.testutil.AssertionUtils.assertFlagsEqual;
import static pl.adrian.testutil.AssertionUtils.assertPacketHeaderEquals;

class BttPacketTest {
    @Test
    void readBttPacket() {
        var headerBytes = new byte[] { 26, 47, -112 };
        var dataBytes = new byte[] {
                27, -106, 0, -108, 0, 83, 111, 109, 101, 32, 84, 121, 112, 101, 100, 32,
                84, 101, 120, 116, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(101, PacketType.BTT, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof BttPacket);

        var castedReadPacket = (BttPacket) readPacket;

        assertPacketHeaderEquals(104, PacketType.BTT, 144, castedReadPacket);
        assertEquals(27, castedReadPacket.getUcid());
        assertEquals(150, castedReadPacket.getClickID());
        assertFlagsEqual(ButtonInstFlag.class, Set.of(), castedReadPacket.getInst());
        assertTrue(castedReadPacket.isInitializeWithButtonText());
        assertEquals(20, castedReadPacket.getTypeInCharacters());
        assertEquals("Some Typed Text", castedReadPacket.getText());
    }
}
