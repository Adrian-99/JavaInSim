package pl.adrian.api.packets.enums;

public enum SmallSubtype {
    NONE,		//  0					: not used
    SSP,		//  1 - instruction		: start sending positions
    SSG,		//  2 - instruction		: start sending gauges
    VTA,		//  3 - report			: vote action
    TMS,		//  4 - instruction		: time stop
    STP,		//  5 - instruction		: time step
    RTP,		//  6 - info			: race time packet (reply to GTH)
    NLI,		//  7 - instruction		: set node lap interval
    ALC,		//  8 - both ways		: set or get allowed cars (TINY_ALC)
    LCS;		//  9 - instruction		: set local car switches (lights, horn, siren)

    private static SmallSubtype[] allValuesCached = null;

    public static SmallSubtype fromOrdinal(int ordinal) {
        if (allValuesCached == null) {
            allValuesCached = values();
        }
        return allValuesCached[ordinal];
    }
}
