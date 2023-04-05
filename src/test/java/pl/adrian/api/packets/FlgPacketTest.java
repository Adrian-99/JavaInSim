package pl.adrian.api.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.packets.enums.FlagType;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.util.PacketReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.adrian.testutil.AssertionUtils.assertPacketHeaderEquals;

class FlgPacketTest {
    @Test
    void readFlgPacket() {
        var headerBytes = new byte[] { 2, 32, 0 };
        var dataBytes = new byte[] { 19, 1, 2, 15, 0 };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(5, PacketType.FLG, 0, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof FlgPacket);

        var castedReadPacket = (FlgPacket) readPacket;

        assertPacketHeaderEquals(8, PacketType.FLG, 0, castedReadPacket);
        assertEquals(19, castedReadPacket.getPlid());
        assertTrue(castedReadPacket.isOn());
        assertEquals(FlagType.CAUSING_YELLOW, castedReadPacket.getFlag());
        assertEquals(15, castedReadPacket.getCarBehind());
    }
}
