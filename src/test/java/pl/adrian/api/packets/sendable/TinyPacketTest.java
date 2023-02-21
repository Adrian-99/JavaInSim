package pl.adrian.api.packets.sendable;

import org.junit.jupiter.api.Test;
import pl.adrian.api.packets.enums.TinySubtype;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class TinyPacketTest {

    @Test
    void createTinyPacket() {
        var packet = new TinyPacket(150, TinySubtype.NONE);
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                1, 3, -106, 0
        };

        assertArrayEquals(expectedBytes, bytes);
    }
}
