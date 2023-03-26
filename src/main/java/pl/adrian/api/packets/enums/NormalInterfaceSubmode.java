package pl.adrian.api.packets.enums;

import pl.adrian.internal.packets.enums.EnumHelpers;

/**
 * Enumeration for normal interface submodes used in {@link pl.adrian.api.packets.CimPacket CimPacket}.
 */
public enum NormalInterfaceSubmode {
    /**
     * value 0: normal
     */
    NORMAL,
    /**
     * value 1: wheel temperatures (F9)
     */
    WHEEL_TEMPS,
    /**
     * value 2: wheel damage (F10)
     */
    WHEEL_DAMAGE,
    /**
     * value 3: live settings (F11)
     */
    LIVE_SETTINGS,
    /**
     * value 4: instructions (F12)
     */
    PIT_INSTRUCTIONS,
    /**
     * value 5: ?
     */
    NUM;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static NormalInterfaceSubmode fromOrdinal(int ordinal) {
        return EnumHelpers.get(NormalInterfaceSubmode.class).fromOrdinal(ordinal);
    }
}
