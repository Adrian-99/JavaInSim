package pl.adrian.api.insim.packets;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class ModPacketTest {
    @Test
    void createModPacket() {
        var packet = new ModPacket(16, 60, 640, 480);
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                5, 15, 0, 0, 16, 0, 0, 0, 60, 0, 0, 0, -128, 2, 0, 0, -32, 1, 0, 0
        };

        assertArrayEquals(expectedBytes, bytes);
    }
}
