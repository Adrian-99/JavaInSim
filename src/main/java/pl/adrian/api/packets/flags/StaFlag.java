package pl.adrian.api.packets.flags;

/**
 * Flags used in state packet.
 */
public enum StaFlag {
    /**
     * bit 0, value 1: in game (or MPR)
     */
    GAME,
    /**
     * bit 1, value 2: in SPR
     */
    REPLAY,
    /**
     * bit 2, value 4: paused
     */
    PAUSED,
    /**
     * bit 3, value 8: SHIFT+U mode
     */
    SHIFTU,
    /**
     * bit 4, value 16: in a dialog
     */
    DIALOG,
    /**
     * bit 5, value 32: FOLLOW view
     */
    SHIFTU_FOLLOW,
    /**
     * bit 6, value 64: SHIFT+U buttons hidden
     */
    SHIFTU_NO_OPT,
    /**
     * bit 7, value 128: showing 2d display
     */
    SHOW_2D,
    /**
     * bit 8, value 256: entry screen
     */
    FRONT_END,
    /**
     * bit 9, value 512: multiplayer mode
     */
    MULTI,
    /**
     * bit 10, value 1024: multiplayer speedup option
     */
    MPSPEEDUP,
    /**
     * bit 11, value 2048: LFS is running in a window
     */
    WINDOWED,
    /**
     * bit 12, value 4096: sound is switched off
     */
    SOUND_MUTE,
    /**
     * bit 13, value 8192: override user view
     */
    VIEW_OVERRIDE,
    /**
     * bit 14, value 16384: InSim buttons visible
     */
    VISIBLE,
    /**
     * bit 15, value 32768: in a text entry dialog
     */
    TEXT_ENTRY
}
