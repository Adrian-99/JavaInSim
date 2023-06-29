package pl.adrian.api.insim.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.insim.packets.enums.TtcSubtype;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class TtcPacketTest {

    @Test
    void createTtcPacket_withReqI() {
        var packet = new TtcPacket(
                TtcSubtype.NONE,
                128,
                129,
                130,
                131,
                150
        );
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                2, 61, -106, 0, -128, -127, -126, -125
        };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void createTtcPacket_withoutReqI() {
        var packet = new TtcPacket(
                TtcSubtype.NONE,
                128,
                129,
                130,
                131
        );
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                2, 61, 0, 0, -128, -127, -126, -125
        };

        assertArrayEquals(expectedBytes, bytes);
    }
}
