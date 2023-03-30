package pl.adrian.api.packets.enums;

import pl.adrian.internal.packets.enums.EnumHelpers;

/**
 * Enumeration for penalty reasons.
 */
public enum PenaltyReason {
    /**
     * value 0: unknown or cleared penalty
     */
    UNKNOWN,
    /**
     * value 1: penalty given by admin
     */
    ADMIN,
    /**
     * value 2: wrong way driving
     */
    WRONG_WAY,
    /**
     * value 3: starting before green light
     */
    FALSE_START,
    /**
     * value 4: speeding in pit lane
     */
    SPEEDING,
    /**
     * value 5: stop-go pit stop too short
     */
    STOP_SHORT,
    /**
     * value 6: compulsory stop is too late
     */
    STOP_LATE,
    /**
     * value 7: ?
     */
    NUM;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static PenaltyReason fromOrdinal(int ordinal) {
        return EnumHelpers.get(PenaltyReason.class).fromOrdinal(ordinal);
    }
}
