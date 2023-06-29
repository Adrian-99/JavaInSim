package pl.adrian.api.insim.packets.enums;

import pl.adrian.internal.common.enums.EnumHelpers;

/**
 * Enumeration for tyre object colours used in
 * {@link pl.adrian.api.insim.packets.structures.objectinfo.TyreObjectInfo TyreObjectInfo}.
 */
public enum TyreObjectColour {
    /**
     * value 0: black
     */
    BLACK,
    /**
     * value 1: white
     */
    WHITE,
    /**
     * value 2: red
     */
    RED,
    /**
     * value 3: blue
     */
    BLUE,
    /**
     * value 4: green
     */
    GREEN,
    /**
     * value 5: yellow
     */
    YELLOW;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static TyreObjectColour fromOrdinal(int ordinal) {
        return EnumHelpers.get(TyreObjectColour.class).fromOrdinal(ordinal);
    }
}
