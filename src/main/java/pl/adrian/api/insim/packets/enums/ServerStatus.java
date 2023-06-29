package pl.adrian.api.insim.packets.enums;

import pl.adrian.internal.common.enums.EnumHelpers;

/**
 * Enumeration for server status.
 */
public enum ServerStatus {
    /**
     * 0 - unknown
     */
    UNKNOWN,
    /**
     * 1 - success
     */
    SUCCESS,
    /**
     * 2 - fail
     */
    FAIL;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static ServerStatus fromOrdinal(int ordinal) {
        return EnumHelpers.get(ServerStatus.class).fromOrdinal(ordinal);
    }
}
