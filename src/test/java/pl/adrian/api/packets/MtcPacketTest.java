package pl.adrian.api.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.packets.enums.MessageSound;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class MtcPacketTest {
    @Test
    void createMtcPacket() {
        var packet = new MtcPacket(MessageSound.MESSAGE, 15, 36, "text sent to single player");
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                9, 14, 0, 1, 15, 36, 0, 0, 116, 101, 120, 116, 32, 115, 101, 110,
                116, 32, 116, 111, 32, 115, 105, 110, 103, 108, 101, 32, 112, 108, 97, 121,
                101, 114, 0, 0
        };

        assertArrayEquals(expectedBytes, bytes);
    }
}
