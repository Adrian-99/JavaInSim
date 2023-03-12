package pl.adrian.internal.packets.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RaceLapsTest {
    @Test
    void createRaceLaps_ForPractice() {
        var raceLaps = new RaceLaps((short) 0);

        assertTrue(raceLaps.isPractice());
        assertFalse(raceLaps.areLaps());
        assertFalse(raceLaps.areHours());
        assertEquals(0, raceLaps.getValue());
    }

    @Test
    void createRaceLaps_ForLaps() {
        var raceLaps = new RaceLaps((short) 50);

        assertFalse(raceLaps.isPractice());
        assertTrue(raceLaps.areLaps());
        assertFalse(raceLaps.areHours());
        assertEquals(50, raceLaps.getValue());
    }

    @Test
    void createRaceLaps_ForHundredsOfLaps() {
        var raceLaps = new RaceLaps((short) 150);

        assertFalse(raceLaps.isPractice());
        assertTrue(raceLaps.areLaps());
        assertFalse(raceLaps.areHours());
        assertEquals(600, raceLaps.getValue());
    }

    @Test
    void createRaceLaps_ForLowHours() {
        var raceLaps = new RaceLaps((short) 200);

        assertFalse(raceLaps.isPractice());
        assertFalse(raceLaps.areLaps());
        assertTrue(raceLaps.areHours());
        assertEquals(10, raceLaps.getValue());
    }

    @Test
    void createRaceLaps_ForInvalidValue() {
        assertThrows(IllegalStateException.class, () -> new RaceLaps((short) 245));
    }
}
