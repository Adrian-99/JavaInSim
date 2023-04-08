package pl.adrian.api.packets.enums;

/**
 * Enumeration for lap timing types used in {@link pl.adrian.api.packets.structures.LapTiming LapTiming}.
 */
public enum LapTimingType {
    /**
     * standard lap timing
     */
    STANDARD,
    /**
     * custom timing - user checkpoints have been placed
     */
    CUSTOM,
    /**
     * no lap timing - e.g. open config with no user checkpoints
     */
    NONE
}
