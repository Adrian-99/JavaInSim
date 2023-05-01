package pl.adrian.api.packets.enums;

import pl.adrian.internal.packets.enums.EnumHelpers;

/**
 * Enumeration for interface modes used in {@link pl.adrian.api.packets.CimPacket CimPacket}.
 */
public enum InterfaceMode {
    /**
     * value 0: not in a special mode
     */
    NORMAL,
    /**
     * value 1: options
     */
    OPTIONS,
    /**
     * value 2: host options
     */
    HOST_OPTIONS,
    /**
     * value 3: garage
     */
    GARAGE,
    /**
     * value 4: car select
     */
    CAR_SELECT,
    /**
     * value 5: track select
     */
    TRACK_SELECT,
    /**
     * value 6: free view mode
     */
    SHIFTU;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static InterfaceMode fromOrdinal(int ordinal) {
        return EnumHelpers.get(InterfaceMode.class).fromOrdinal(ordinal);
    }
}
