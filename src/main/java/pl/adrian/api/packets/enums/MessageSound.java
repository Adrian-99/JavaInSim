package pl.adrian.api.packets.enums;

/**
 * Enumeration for sounds to be played while sending message.
 */
public enum MessageSound {
    /**
     * 0 - no sound
     */
    SILENT,
    /**
     * 1 - message sound
     */
    MESSAGE,
    /**
     * 2 - system message sound
     */
    SYSMESSAGE,
    /**
     * 3 - invalid key sound
     */
    INVALIDKEY,
    /**
     * 4 - error sound
     */
    ERROR,
    /**
     * 5 - num sound
     */
    NUM
}
