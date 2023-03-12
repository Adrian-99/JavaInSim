package pl.adrian.api.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.packets.flags.Car;
import pl.adrian.api.packets.structures.CarHandicaps;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class HcpPacketTest {
    @Test
    void createHcpPacket() {
        var packet = new HcpPacket(Map.of(
                Car.LX6, new CarHandicaps(5, 15),
                Car.RAC, new CarHandicaps(15, 20),
                Car.FZ5, new CarHandicaps(15, 25)
        ));
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                17, 56, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                5, 15, 0, 0, 0, 0, 15, 20, 15, 25, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0
        };

        assertArrayEquals(expectedBytes, bytes);
    }
}
