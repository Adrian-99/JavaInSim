package pl.adrian.api.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.packets.enums.TtcSubtype;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class TtcPacketTest {

    @Test
    void createTtcPacket() {
        var packet = new TtcPacket(
                150,
                TtcSubtype.NONE,
                128,
                129,
                130,
                131
        );
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                2, 61, -106, 0, -128, -127, -126, -125
        };

        assertArrayEquals(expectedBytes, bytes);
    }
}
