package pl.adrian.api.packets.enums;

/**
 * Enumeration for subtype of ttc packet
 */
public enum TtcSubtype {
    /**
     * 0: not used
     */
    NONE,
    /**
     * 1 - info request: send IS_AXM for a layout editor selection
     */
    SEL,
    /**
     * 2 - info request: send IS_AXM every time the selection changes
     */
    SEL_START,
    /**
     * 3 - instruction: switch off IS_AXM requested by TTC_SEL_START
     */
    SEL_STOP
}
