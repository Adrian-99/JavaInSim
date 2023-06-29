package pl.adrian.api.insim.packets.enums;

import pl.adrian.internal.common.enums.EnumHelpers;

/**
 * Enumeration for race progress.
 */
public enum RaceProgress {
    /**
     * 0 - no race
     */
    NO_RACE,
    /**
     * 1 - race
     */
    RACE,
    /**
     * 2 - qualifying
     */
    QUALIFYING;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static RaceProgress fromOrdinal(int ordinal) {
        return EnumHelpers.get(RaceProgress.class).fromOrdinal(ordinal);
    }
}
