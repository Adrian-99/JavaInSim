package pl.adrian.api.insim.packets.enums;

import pl.adrian.internal.common.enums.EnumHelpers;

/**
 * Enumeration for admin command result.
 */
public enum AcrResult {
    /**
     * 0 - none (unused)
     */
    NONE,
    /**
     * 1 - processed
     */
    PROCESSED,
    /**
     * 2 - rejected
     */
    REJECTED,
    /**
     * 3 - unknown command
     */
    UNKNOWN_COMMAND;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static AcrResult fromOrdinal(int ordinal) {
        return EnumHelpers.get(AcrResult.class).fromOrdinal(ordinal);
    }
}
