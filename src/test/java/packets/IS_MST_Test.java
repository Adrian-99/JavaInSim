package packets;

import org.junit.jupiter.api.Test;
import pl.adrian.packets.IS_MST;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class IS_MST_Test {

    @Test
    void create_IS_MST() {
        var packet = new IS_MST(
                150,
                "The test message to be sent"
        );
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                17, 13, -106, 0, 84, 104, 101, 32, 116, 101, 115, 116, 32, 109, 101, 115,
                115, 97, 103, 101, 32, 116, 111, 32, 98, 101, 32, 115, 101, 110, 116, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0
        };

        assertArrayEquals(expectedBytes, bytes);
    }
}
