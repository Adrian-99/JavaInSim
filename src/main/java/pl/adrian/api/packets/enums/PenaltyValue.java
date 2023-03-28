package pl.adrian.api.packets.enums;

import pl.adrian.internal.packets.enums.EnumHelpers;

/**
 * Enumeration for penalty values.
 */
public enum PenaltyValue {
    /**
     * value 0: none
     */
    NONE,
    /**
     * value 1: drive-through
     */
    DT,
    /**
     * value 2: drive-through (can now be cleared)
     */
    DT_VALID,
    /**
     * value 3: stop-go
     */
    SG,
    /**
     * value 4: stop-go (can now be cleared)
     */
    SG_VALID,
    /**
     * value 5: 30 second time
     */
    PLUS_30_S,
    /**
     * value 6: 45 second time
     */
    PLUS_45_S,
    /**
     * value 7: ?
     */
    NUM;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static PenaltyValue fromOrdinal(int ordinal) {
        return EnumHelpers.get(PenaltyValue.class).fromOrdinal(ordinal);
    }
}
