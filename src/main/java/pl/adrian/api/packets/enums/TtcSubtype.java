package pl.adrian.api.packets.enums;

import pl.adrian.api.packets.AxmPacket;

/**
 * Enumeration for subtype of ttc packet
 */
public enum TtcSubtype {
    /**
     * 0: not used
     */
    NONE,
    /**
     * 1 - info request: send {@link AxmPacket} for a layout editor selection
     */
    SEL,
    /**
     * 2 - info request: send {@link AxmPacket} every time the selection changes
     */
    SEL_START,
    /**
     * 3 - instruction: switch off {@link AxmPacket} requested by {@link #SEL_START}
     */
    SEL_STOP
}
