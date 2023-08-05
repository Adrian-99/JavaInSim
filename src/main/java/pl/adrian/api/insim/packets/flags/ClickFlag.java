package pl.adrian.api.insim.packets.flags;

import pl.adrian.api.insim.packets.BtcPacket;

/**
 * Enumeration for button click flags used in {@link BtcPacket}.
 */
public enum ClickFlag {
    /**
     * bit 0, value 1: left click
     */
    LMB,
    /**
     * bit 1, value 2: right click
     */
    RMB,
    /**
     * bit 2, value 4: ctrl + click
     */
    CTRL,
    /**
     * bit 3, value 8: shift + click
     */
    SHIFT
}
