package pl.adrian.api.packets;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class ReoPacketTest {
    @Test
    void createReoPacket() {
        var packet = new ReoPacket(List.of(
                1, 17, 5, 2, 18, 19, 28, 21, 3, 23, 14, 11, 9, 7, 6, 25,
                27, 30, 15, 20, 26, 22, 4, 16, 31, 29, 10, 12, 13, 24, 8
        ));
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                11, 36, 0, 31, 1, 17, 5, 2, 18, 19, 28, 21, 3, 23, 14, 11,
                9, 7, 6, 25, 27, 30, 15, 20, 26, 22, 4, 16, 31, 29, 10, 12,
                13, 24, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0
        };

        assertArrayEquals(expectedBytes, bytes);
    }
}
