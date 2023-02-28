package pl.adrian.api.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.packets.enums.SmallSubtype;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class SmallPacketTest {

    @Test
    void createSmallPacket_withReqI() {
        var packet = new SmallPacket(SmallSubtype.NONE, 3885174239L, 150);
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                2, 4, -106, 0, -33, 13, -109, -25
        };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void createSmallPacket_withoutReqI() {
        var packet = new SmallPacket(SmallSubtype.NONE, 3885174239L);
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                2, 4, 0, 0, -33, 13, -109, -25
        };

        assertArrayEquals(expectedBytes, bytes);
    }
}
