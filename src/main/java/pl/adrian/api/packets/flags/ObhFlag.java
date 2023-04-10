package pl.adrian.api.packets.flags;

/**
 * Enumeration for flags used in {@link pl.adrian.api.packets.ObhPacket ObhPacket}.
 */
public enum ObhFlag {
    /**
     * bit 0, value 1: an added object
     */
    LAYOUT,
    /**
     * bit 1, value 2: a movable object
     */
    CAN_MOVE,
    /**
     * bit 2, value 4: was moving before this hit
     */
    WAS_MOVING,
    /**
     * bit 3, value 8: object in original position
     */
    ON_SPOT
}
