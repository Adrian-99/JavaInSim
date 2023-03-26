package pl.adrian.api.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.packets.enums.SmallSubtype;
import pl.adrian.api.packets.enums.DefaultCar;
import pl.adrian.api.packets.flags.LcsFlag;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class SmallPacketTest {

    @Test
    void createSmallPacket_withReqI() {
        var packet = new SmallPacket(SmallSubtype.NONE, 3885174239L, 150);
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                2, 4, -106, 0, -33, 13, -109, -25
        };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void createSmallPacket_withoutReqI() {
        var packet = new SmallPacket(SmallSubtype.NONE, 3885174239L);
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                2, 4, 0, 0, -33, 13, -109, -25
        };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void createSmallPacket_withLcsFlags() {
        var packet = new SmallPacket(LcsFlag.SIGNALS_LEFT, LcsFlag.HEADLIGHTS_OFF, LcsFlag.HORN_1);
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                2, 4, 0, 9, 13, 1, 1, 0
        };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void createSmallPacket_withCars() {
        var packet = new SmallPacket(DefaultCar.FXR, DefaultCar.XRR, DefaultCar.FZR);
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                2, 4, 0, 8, 0, -128, 3, 0
        };

        assertArrayEquals(expectedBytes, bytes);
    }
}
