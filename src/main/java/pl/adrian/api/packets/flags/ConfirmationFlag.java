package pl.adrian.api.packets.flags;

import pl.adrian.internal.packets.flags.FlagWithCustomBehavior;

/**
 * Enumeration for confirmation flags.
 */
public enum ConfirmationFlag implements FlagWithCustomBehavior {
    /**
     * bit 0, value 1: mentioned
     */
    MENTIONED,
    /**
     * bit 1, value 2: confirmed
     */
    CONFIRMED,
    /**
     * bit 2, value 4: drive-through penalty
     */
    PENALTY_DT,
    /**
     * bit 3, value 8: stop-go penalty
     */
    PENALTY_SG,
    /**
     * bit 4, value 16: 30 seconds penalty
     */
    PENALTY_30,
    /**
     * bit 5, value 32: 45 seconds penalty
     */
    PENALTY_45,
    /**
     * bit 6, value 64: did not pit
     */
    DID_NOT_PIT,
    /**
     * bit 2 | 3 | 6, value 4 | 8 | 64: disqualified
     */
    DISQ,
    /**
     * bit 4 | 5, value 16 | 32: time penalty
     */
    TIME;

    @Override
    public boolean isPresent(long flagsValue) {
        switch (this) {
            case DISQ -> {
                return (flagsValue & 76) > 0;
            }
            case TIME -> {
                return (flagsValue & 48) > 0;
            }
            default -> {
                var flagValue = 1 << ordinal();
                return (flagsValue & flagValue) == flagValue;
            }
        }
    }

    @Override
    public int getValue() {
        return switch (this) {
            case DISQ, TIME -> 0;
            default -> 1 << ordinal();
        };
    }
}
