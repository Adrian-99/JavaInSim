package pl.adrian.api.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.packets.enums.SmallSubtype;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class SmallPacketTest {

    @Test
    void createSmallPacket() {
        var packet = new SmallPacket(150, SmallSubtype.NONE, 3885174239L);
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                2, 4, -106, 0, -33, 13, -109, -25
        };

        assertArrayEquals(expectedBytes, bytes);
    }
}
