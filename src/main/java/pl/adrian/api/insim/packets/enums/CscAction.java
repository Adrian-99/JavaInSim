package pl.adrian.api.insim.packets.enums;

import pl.adrian.api.insim.packets.CscPacket;
import pl.adrian.internal.common.enums.EnumHelpers;

/**
 * Enumeration for actions used in {@link CscPacket}.
 */
public enum CscAction {
    /**
     * value 0: stop
     */
    STOP,
    /**
     * value 1: start
     */
    START;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static CscAction fromOrdinal(int ordinal) {
        return EnumHelpers.get(CscAction.class).fromOrdinal(ordinal);
    }
}
