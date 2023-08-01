package pl.adrian.api.insim.packets.enums;

import pl.adrian.api.insim.packets.SshPacket;
import pl.adrian.internal.common.enums.EnumHelpers;

/**
 * Enumeration for error codes used in {@link SshPacket}.
 */
public enum SshErrorCode {
    /**
     * value 0: OK: completed instruction
     */
    OK,
    /**
     * value 1: can't save a screenshot - dedicated host
     */
    DEDICATED,
    /**
     * value 2: {@link SshPacket} corrupted (e.g. Name does not end with zero)
     */
    CORRUPTED,
    /**
     * value 3: could not save the screenshot
     */
    NO_SAVE;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static SshErrorCode fromOrdinal(int ordinal) {
        return EnumHelpers.get(SshErrorCode.class).fromOrdinal(ordinal);
    }
}
