package pl.adrian.api.packets.flags;

/**
 * Enumeration for confirmation flags.
 */
public enum ConfirmationFlag {
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
    DID_NOT_PIT
}
