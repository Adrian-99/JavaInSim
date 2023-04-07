package pl.adrian.api.packets.enums;

import pl.adrian.internal.packets.enums.EnumHelpers;

/**
 * Enumeration for marshall type used in {@link pl.adrian.api.packets.structures.RestrictedAreaInfo RestrictedAreaInfo}.
 */
public enum MarshallType {
    /**
     * value 0: no marshall
     */
    NO_MARSHALL,
    /**
     * value 1: standing marshall
     */
    STANDING,
    /**
     * value 2: marshall pointing left
     */
    POINTING_LEFT,
    /**
     * value 3: marshall pointing right
     */
    POINTING_RIGHT;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static MarshallType fromOrdinal(int ordinal) {
        return EnumHelpers.get(MarshallType.class).fromOrdinal(ordinal);
    }
}
