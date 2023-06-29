package pl.adrian.api.insim.packets.enums;

import pl.adrian.internal.common.enums.EnumHelpers;

/**
 * Enumeration for actions used in {@link pl.adrian.api.insim.packets.UcoPacket UcoPacket}.
 */
public enum UcoAction {
    /**
     * value 0: entered a circle
     */
    CIRCLE_ENTER,
    /**
     * value 1: left a circle
     */
    CIRCLE_LEAVE,
    /**
     * value 2: crossed cp in forward direction
     */
    CP_FWD,
    /**
     * value 3: crossed cp in reverse direction
     */
    CP_REV;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static UcoAction fromOrdinal(int ordinal) {
        return EnumHelpers.get(UcoAction.class).fromOrdinal(ordinal);
    }
}
