package pl.adrian.api.insim.packets.enums;

import pl.adrian.internal.common.enums.EnumHelpers;
import pl.adrian.internal.common.enums.EnumWithCustomValue;

/**
 * Enumeration for view identifiers.
 */
public enum ViewIdentifier implements EnumWithCustomValue {
    /**
     * value 0 - arcade
     */
    FOLLOW,
    /**
     * value 1 - helicopter
     */
    HELI,
    /**
     * value 2 - tv camera
     */
    CAM,
    /**
     * value 3 - cockpit
     */
    DRIVER,
    /**
     * value 4 - custom
     */
    CUSTOM,
    /**
     * value 5 - ???
     */
    MAX,
    /**
     * value 255 - viewing another car
     */
    ANOTHER;

    @Override
    public int getValue() {
        return !this.equals(ANOTHER) ? ordinal() : 255;
    }

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static ViewIdentifier fromOrdinal(int ordinal) {
        return ordinal != 255 ? EnumHelpers.get(ViewIdentifier.class).fromOrdinal(ordinal) : ANOTHER;
    }
}
