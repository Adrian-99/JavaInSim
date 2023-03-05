package pl.adrian.api.packets.enums;

import pl.adrian.internal.packets.enums.EnumHelpers;

/**
 * Enumeration for vote actions.
 */
public enum VoteAction {
    /**
     * 0 - no vote
     */
    NONE,
    /**
     * 1 - end race
     */
    END,
    /**
     * 2 - restart
     */
    RESTART,
    /**
     * 3 - qualify
     */
    QUALIFY,
    /**
     * 4 - ???
     */
    NUM;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static VoteAction fromOrdinal(int ordinal) {
        return EnumHelpers.get(VoteAction.class).fromOrdinal(ordinal);
    }
}
