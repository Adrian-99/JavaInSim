package pl.adrian.api.packets.enums;

/**
 * Enumeration for subtype of small packet
 */
public enum SmallSubtype {
    /**
     * 0: not used
     */
    NONE,
    /**
     * 1 - instruction: start sending positions
     */
    SSP,
    /**
     * 2 - instruction: start sending gauges
     */
    SSG,
    /**
     * 3 - report: vote action
     */
    VTA,
    /**
     * 4 - instruction: time stop
     */
    TMS,
    /**
     * 5 - instruction: time step
     */
    STP,
    /**
     * 6 - info: race time packet (reply to GTH)
     */
    RTP,
    /**
     * 7 - instruction: set node lap interval
     */
    NLI,
    /**
     * 8 - both ways: set or get allowed cars (TINY_ALC)
     */
    ALC,
    /**
     * 9 - instruction: set local car switches (lights, horn, siren)
     */
    LCS;

    private static SmallSubtype[] allValuesCached = null;

    /**
     * Converts ordinal number to enum value
     * @param ordinal ordinal number
     * @return enum value
     */
    public static SmallSubtype fromOrdinal(int ordinal) {
        if (allValuesCached == null) {
            allValuesCached = values();
        }
        return allValuesCached[ordinal];
    }
}
