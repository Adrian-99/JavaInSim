package pl.adrian.internal.packets.util;

/**
 * This class helps to interpret race laps value returned from LFS.
 */
public class RaceLaps {
    private final short value;
    private final RaceLapsUnit unit;

    /**
     * Creates new race laps instance.
     * @param byteValue byte value returned from LFS
     */
    public RaceLaps(short byteValue) {
        if (byteValue == 0) {
            value = 0;
            unit = RaceLapsUnit.PRACTICE;
        } else if (byteValue > 0 && byteValue < 100) {
            value = byteValue;
            unit = RaceLapsUnit.LAPS;
        } else if (byteValue > 99 && byteValue < 191) {
            value = (short) ((byteValue - 100) * 10 + 100);
            unit = RaceLapsUnit.LAPS;
        } else if (byteValue > 190 && byteValue < 239) {
            value = (short) (byteValue - 190);
            unit = RaceLapsUnit.HOURS;
        } else {
            throw new IllegalStateException("Invalid byte value for RaceLaps: " + byteValue);
        }
    }

    /**
     * @return whether value represents practice
     */
    public boolean isPractice() {
        return unit.equals(RaceLapsUnit.PRACTICE);
    }

    /**
     * @return whether unit of value is lap
     */
    public boolean areLaps() {
        return unit.equals(RaceLapsUnit.LAPS);
    }

    /**
     * @return whether unit of value is hour
     */
    public boolean areHours() {
        return unit.equals(RaceLapsUnit.HOURS);
    }

    /**
     * @return value of race laps - 0 means it's practice, to check unit for values >0 use
     * {@link #areLaps} and {@link #areHours} methods
     */
    public short getValue() {
        return value;
    }

    private enum RaceLapsUnit {
        PRACTICE,
        LAPS,
        HOURS
    }
}
