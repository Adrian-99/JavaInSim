package pl.adrian.api.insim.packets.flags;

/**
 * Enumeration for setup flags.
 */
public enum SetupFlag {
    /**
     * bit 0, value 1: symmetric wheels
     */
    SYMM_WHEELS,
    /**
     * bit 1, value 2: traction control enabled
     */
    TC_ENABLE,
    /**
     * bit 2, value 4: ABS enabled
     */
    ABS_ENABLE
}
