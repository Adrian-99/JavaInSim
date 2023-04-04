package pl.adrian.api.packets.structures;

import org.junit.jupiter.api.Test;
import pl.adrian.api.packets.enums.DefaultCar;
import pl.adrian.internal.packets.exceptions.PacketReadingException;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {
    @Test
    void create_forDefaultCar() {
        var cNameBytes = new short[] { 76, 88, 54, 0 };
        var car = new Car(cNameBytes);

        assertTrue(car.getDefaultCar().isPresent());
        assertEquals(DefaultCar.LX6, car.getDefaultCar().get());
        assertEquals("LX6", car.getSkinId());
        assertFalse(car.isMod());
    }

    @Test
    void create_forModCar() {
        var cNameBytes = new short[] { 9, 222, 53, 0 };
        var car = new Car(cNameBytes);

        assertTrue(car.getDefaultCar().isEmpty());
        assertEquals("35DE09", car.getSkinId());
        assertTrue(car.isMod());
    }

    @Test
    void create_withIncorrectCNameBytesLength() {
        var cNameBytes = new short[] { 15, 112, 221, 67, 0 };
        assertThrows(PacketReadingException.class, () -> new Car(cNameBytes));
    }

    @Test
    void create_withIncorrectCNameBytesContent() {
        var cNameBytes = new short[] { 15, 112, 221, 67 };
        assertThrows(PacketReadingException.class, () -> new Car(cNameBytes));
    }
}
