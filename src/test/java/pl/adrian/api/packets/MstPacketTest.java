package pl.adrian.api.packets;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class MstPacketTest {

    @Test
    void createMstPacket() {
        var packet = new MstPacket("The test message to be sent");
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                17, 13, 0, 0, 84, 104, 101, 32, 116, 101, 115, 116, 32, 109, 101, 115,
                115, 97, 103, 101, 32, 116, 111, 32, 98, 101, 32, 115, 101, 110, 116, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0
        };

        assertArrayEquals(expectedBytes, bytes);
    }
}
