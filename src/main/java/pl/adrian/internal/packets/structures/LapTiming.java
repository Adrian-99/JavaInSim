package pl.adrian.internal.packets.structures;

/**
 * This class helps to interpret lap timing info returned from LFS.
 */
public class LapTiming {
    private final LapTimingType type;
    private final short numberOfCheckpoints;

    /**
     * Creates new lap timing instance.
     * @param byteValue byte value returned from LFS
     */
    public LapTiming(short byteValue) {
        switch (byteValue & 0xc0) {
            case 0x80 -> type = LapTimingType.CUSTOM;
            case 0xc0 -> type = LapTimingType.NONE;
            default /*0x40*/ -> type = LapTimingType.STANDARD;
        }
        numberOfCheckpoints = (short) (byteValue & 0x03);
    }

    /**
     * @return whether standard lap timing is being used
     */
    public boolean isStandardLapTiming() {
        return type.equals(LapTimingType.STANDARD);
    }

    /**
     * @return whether custom lap timing is being used (user checkpoints have been placed)
     */
    public boolean isCustomLapTiming() {
        return type.equals(LapTimingType.CUSTOM);
    }

    /**
     * @return whether no lap timing is being used (e.g. open config with no user checkpoints)
     */
    public boolean isNoLapTiming() {
        return type.equals(LapTimingType.NONE);
    }

    /**
     * @return number of checkpoints if lap timing is enabled
     */
    public short getNumberOfCheckpoints() {
        return numberOfCheckpoints;
    }

    private enum LapTimingType {
        STANDARD,
        CUSTOM,
        NONE
    }
}
