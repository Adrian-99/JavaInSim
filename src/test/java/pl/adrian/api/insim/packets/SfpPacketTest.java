package pl.adrian.api.insim.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.insim.packets.enums.SfpFlag;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class SfpPacketTest {
    @Test
    void createSfpPacket() {
        var packet = new SfpPacket(SfpFlag.MPSPEEDUP, true);
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                2, 7, 0, 0, 0, 4, 1, 0
        };

        assertArrayEquals(expectedBytes, bytes);
    }
}
