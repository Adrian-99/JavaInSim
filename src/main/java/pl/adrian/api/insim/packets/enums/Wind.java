package pl.adrian.api.insim.packets.enums;

import pl.adrian.internal.common.enums.EnumHelpers;

/**
 * Enumeration for wind.
 */
public enum Wind {
    /**
     * 0 - off
     */
    OFF,
    /**
     * 1 - weak
     */
    WEAK,
    /**
     * 2 - strong
     */
    STRONG;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static Wind fromOrdinal(int ordinal) {
        return EnumHelpers.get(Wind.class).fromOrdinal(ordinal);
    }
}
