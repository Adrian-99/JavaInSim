package pl.adrian.api.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.packets.enums.TinySubtype;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class TinyPacketTest {

    @Test
    void createTinyPacket_withReqI() {
        var packet = new TinyPacket(TinySubtype.NONE, 150);
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                1, 3, -106, 0
        };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void createTinyPacket_withoutReqI() {
        var packet = new TinyPacket(TinySubtype.NONE);
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                1, 3, 0, 0
        };

        assertArrayEquals(expectedBytes, bytes);
    }
}
