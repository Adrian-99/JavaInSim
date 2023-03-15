package pl.adrian.internal.packets.structures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LapTimingTest {
    @Test
    void createLapTiming_forStandardTiming() {
        var lapTiming = new LapTiming((short) 67);

        assertTrue(lapTiming.isStandardLapTiming());
        assertFalse(lapTiming.isCustomLapTiming());
        assertFalse(lapTiming.isNoLapTiming());
        assertEquals(3, lapTiming.getNumberOfCheckpoints());
    }

    @Test
    void createLapTiming_forCustomTiming() {
        var lapTiming = new LapTiming((short) 130);

        assertFalse(lapTiming.isStandardLapTiming());
        assertTrue(lapTiming.isCustomLapTiming());
        assertFalse(lapTiming.isNoLapTiming());
        assertEquals(2, lapTiming.getNumberOfCheckpoints());
    }

    @Test
    void createLapTiming_forNoTiming() {
        var lapTiming = new LapTiming((short) 192);

        assertFalse(lapTiming.isStandardLapTiming());
        assertFalse(lapTiming.isCustomLapTiming());
        assertTrue(lapTiming.isNoLapTiming());
        assertEquals(0, lapTiming.getNumberOfCheckpoints());
    }
}
