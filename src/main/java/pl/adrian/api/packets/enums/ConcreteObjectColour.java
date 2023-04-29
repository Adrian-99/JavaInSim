package pl.adrian.api.packets.enums;

import pl.adrian.internal.packets.enums.EnumHelpers;

/**
 * Enumeration for colours of concrete objects used in
 * {@link pl.adrian.api.packets.structures.objectinfo.ConcreteObjectInfo ConcreteObjectInfo}
 * and its inheritors.
 */
public enum ConcreteObjectColour {
    /**
     * value 0: grey
     */
    GREY,
    /**
     * value 1: red
     */
    RED,
    /**
     * value 2: blue
     */
    BLUE,
    /**
     * Value 3: yellow
     */
    YELLOW;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static ConcreteObjectColour fromOrdinal(int ordinal) {
        return EnumHelpers.get(ConcreteObjectColour.class).fromOrdinal(ordinal);
    }
}
