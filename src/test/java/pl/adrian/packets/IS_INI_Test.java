package pl.adrian.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.packets.flags.Flags;
import pl.adrian.packets.flags.ISF;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class IS_INI_Test {

    @Test
    void create_IS_INI() {
        var packet = new IS_INI(
                new Flags<>(ISF.LOCAL, ISF.MSO_COLS, ISF.MCI, ISF.AXM_LOAD),
                '!',
                500,
                "admin123",
                "test app"
        );
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                11, 1, 1, 0, 0, 0, 44, 2, 9, 33, -12, 1, 97, 100, 109, 105,
                110, 49, 50, 51, 0, 0, 0, 0, 0, 0, 0, 0, 116, 101, 115, 116,
                32, 97, 112, 112, 0, 0, 0, 0, 0, 0, 0, 0
        };

        assertArrayEquals(expectedBytes, bytes);
    }
}
