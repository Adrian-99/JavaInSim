package pl.adrian.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.packets.enums.TtcSubtype;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class IS_TTC_Test {

    @Test
    void create_IS_TTC() {
        var packet = new IS_TTC(
                150,
                TtcSubtype.TTC_NONE,
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
