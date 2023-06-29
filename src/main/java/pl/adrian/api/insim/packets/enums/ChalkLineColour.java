package pl.adrian.api.insim.packets.enums;

import pl.adrian.internal.common.enums.EnumHelpers;

/**
 * Enumeration for colours of chalk line used in
 * {@link pl.adrian.api.insim.packets.structures.objectinfo.ChalkLineInfo ChalkLineInfo}.
 */
public enum ChalkLineColour {
    /**
     * value 0: white
     */
    WHITE,
    /**
     * value 1: red
     */
    RED,
    /**
     * value 2: blue
     */
    BLUE,
    /**
     * value 3: yellow
     */
    YELLOW;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static ChalkLineColour fromOrdinal(int ordinal) {
        return EnumHelpers.get(ChalkLineColour.class).fromOrdinal(ordinal);
    }
}
