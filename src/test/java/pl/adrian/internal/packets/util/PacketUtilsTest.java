package pl.adrian.internal.packets.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PacketUtilsTest {
    @Test
    void getLfsCharArraySize_forNullText() {
        var size = PacketUtils.getLfsCharArraySize(null, 16);

        assertEquals(4, size);
    }
    @Test
    void getLfsCharArraySize_forEmptyText() {
        var size = PacketUtils.getLfsCharArraySize("", 16);

        assertEquals(4, size);
    }

    @Test
    void getLfsCharArraySize_forNormalText() {
        var size = PacketUtils.getLfsCharArraySize("testtes", 16);

        assertEquals(8, size);
    }

    @Test
    void getLfsCharArraySize_forNormalText2() {
        var size = PacketUtils.getLfsCharArraySize("testtestt", 16);

        assertEquals(12, size);
    }

    @Test
    void getLfsCharArraySize_forTooLongText() {
        var size = PacketUtils.getLfsCharArraySize("testtesttesttesttest", 16);

        assertEquals(16, size);
    }

    @Test
    void getLfsCharArraySize_forIncorrectMaxValue() {
        var size = PacketUtils.getLfsCharArraySize("testtesttesttesttest", 14);

        assertEquals(12, size);
    }
}
