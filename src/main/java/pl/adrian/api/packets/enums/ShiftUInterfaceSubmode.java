package pl.adrian.api.packets.enums;

import pl.adrian.internal.packets.enums.EnumHelpers;

/**
 * Enumeration for free view interface submodes used in {@link pl.adrian.api.packets.CimPacket CimPacket}.
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
    EDIT,
    /**
     * value 3: ?
     */
    NUM;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static ShiftUInterfaceSubmode fromOrdinal(int ordinal) {
        return EnumHelpers.get(ShiftUInterfaceSubmode.class).fromOrdinal(ordinal);
    }
}