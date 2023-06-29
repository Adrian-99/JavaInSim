package pl.adrian.api.insim.packets.enums;

import pl.adrian.internal.common.enums.EnumHelpers;

/**
 * Enumeration for free view interface submodes used in {@link pl.adrian.api.insim.packets.CimPacket CimPacket}.
 */
public enum ShiftUInterfaceSubmode {
    /**
     * value 0: no buttons displayed
     */
    PLAIN,
    /**
     * value 1: buttons displayed (not editing)
     */
    BUTTONS,
    /**
     * value 2: edit mode
     */
    EDIT;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static ShiftUInterfaceSubmode fromOrdinal(int ordinal) {
        return EnumHelpers.get(ShiftUInterfaceSubmode.class).fromOrdinal(ordinal);
    }
}
