package pl.adrian.api.packets.flags;

/**
 * Flags used in {@link pl.adrian.api.packets.IsiPacket IsiPacket}.
 */
public enum IsiFlag {
    /**
     * bit 0, value 1: spare
     */
    RES_0,
    /**
     * bit 1, value 2: spare
     */
    RES_1,
    /**
     * bit 2, value 4: guest or single player
     */
    LOCAL,
    /**
     * bit 3, value 8: keep colours in MSO text
     */
    MSO_COLS,
    /**
     * bit 4, value 16: receive NLP packets
     */
    NLP,
    /**
     * bit 5, value 32: receive MCI packets
     */
    MCI,
    /**
     * bit 6, value 64: receive CON packets
     */
    CON,
    /**
     * bit 7, value 128: receive OBH packets
     */
    OBH,
    /**
     * bit 8, value 256: receive HLV packets
     */
    HLV,
    /**
     * bit 9, value 512: receive AXM when loading a layout
     */
    AXM_LOAD,
    /**
     * bit 10, value 1024: receive AXM when changing objects
     */
    AXM_EDIT,
    /**
     * bit 11, value 2048: process join requests
     */
    REQ_JOIN
}
