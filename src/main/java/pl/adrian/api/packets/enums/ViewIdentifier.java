package pl.adrian.api.packets.enums;

import pl.adrian.internal.packets.enums.EnumHelpers;

/**
 * Enumeration for view identifiers.
 */
public enum ViewIdentifier {
    /**
     * 0 - arcade
     */
    FOLLOW,
    /**
     * 1 - helicopter
     */
    HELI,
    /**
     * 2 - tv camera
     */
    CAM,
    /**
     * 3 - cockpit
     */
    DRIVER,
    /**
     * 4 - custom
     */
    CUSTOM,
    /**
     * 5 - ???
     */
    MAX;

    // const int VIEW_ANOTHER = 255; // viewing another car

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static ViewIdentifier fromOrdinal(int ordinal) {
        return EnumHelpers.get(ViewIdentifier.class).fromOrdinal(ordinal);
    }
}
