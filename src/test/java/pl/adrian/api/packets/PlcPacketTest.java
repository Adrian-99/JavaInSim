package pl.adrian.api.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.packets.enums.DefaultCar;
import pl.adrian.api.packets.flags.Flags;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class PlcPacketTest {
    @Test
    void createPlcPacket() {
        var packet = new PlcPacket(21, new Flags<>(DefaultCar.RB4, DefaultCar.FXO, DefaultCar.XRT));
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] { 3, 53, 0, 0, 21, 0, 0, 0, 28, 0, 0, 0 };

        assertArrayEquals(expectedBytes, bytes);
    }
}
