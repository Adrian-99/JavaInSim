package pl.adrian.api.packets.enums;

import pl.adrian.internal.packets.enums.EnumHelpers;

/**
 * Enumeration for pit lane facts.
 */
public enum PitLaneFact {
    /**
     * value 0: left pit lane
     */
    EXIT,
    /**
     * value 1: entered pit lane
     */
    ENTER,
    /**
     * value 2: entered for no purpose
     */
    NO_PURPOSE,
    /**
     * value 3: entered for drive-through
     */
    DT,
    /**
     * value 4: entered for stop-go
     */
    SG,
    /**
     * value 5: ?
     */
    NUM;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static PitLaneFact fromOrdinal(int ordinal) {
        return EnumHelpers.get(PitLaneFact.class).fromOrdinal(ordinal);
    }
}
