package pl.adrian.api.insim.packets.enums;

import pl.adrian.internal.common.enums.EnumHelpers;

/**
 * Enumeration for leave reason used in {@link pl.adrian.api.insim.packets.CnlPacket CnlPacket}.
 */
public enum LeaveReason {
    /**
     * value 0: none
     */
    DISCO,
    /**
     * value 1: timed out
     */
    TIMEOUT,
    /**
     * value 2: lost connection
     */
    LOSTCONN,
    /**
     * value 3: kicked
     */
    KICKED,
    /**
     * value 4: banned
     */
    BANNED,
    /**
     * value 5: security
     */
    SECURITY,
    /**
     * value 6: cheat protection wrong
     */
    CPW,
    /**
     * value 7: out of sync with host
     */
    OOS,
    /**
     * value 8: join OOS (initial sync failed)
     */
    JOOS,
    /**
     * value 9: invalid packet
     */
    HACK;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static LeaveReason fromOrdinal(int ordinal) {
        return EnumHelpers.get(LeaveReason.class).fromOrdinal(ordinal);
    }
}
