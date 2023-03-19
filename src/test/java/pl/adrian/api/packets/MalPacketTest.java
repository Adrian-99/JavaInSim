package pl.adrian.api.packets;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class MalPacketTest {
    @Test
    void createMalPacket() {
        var packet = new MalPacket(List.of("31A475", "DB2790", "2C6037", "A03FE5"));
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                6, 65, 0, 4, 0, 0, 0, 0, 117, -92, 49, 0, -112, 39, -37, 0,
                55, 96, 44, 0, -27, 63, -96, 0
        };

        assertArrayEquals(expectedBytes, bytes);
    }
}
