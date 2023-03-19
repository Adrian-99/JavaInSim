package pl.adrian.internal.packets.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PacketUtilsTest {
    @Test
    void getPacketSize_forNullText() {
        var size = PacketUtils.getPacketSize(4, null, 16);

        assertEquals(8, size);
    }
    @Test
    void getPacketSize_forEmptyText() {
        var size = PacketUtils.getPacketSize(4, "", 16);

        assertEquals(8, size);
    }

    @Test
    void getPacketSize_forNormalText() {
        var size = PacketUtils.getPacketSize(4, "testtes", 16);

        assertEquals(12, size);
    }

    @Test
    void getPacketSize_forNormalText2() {
        var size = PacketUtils.getPacketSize(4, "testtestt", 16);

        assertEquals(16, size);
    }

    @Test
    void getPacketSize_forTooLongText() {
        var size = PacketUtils.getPacketSize(4, "testtesttesttesttest", 16);

        assertEquals(20, size);
    }
}
