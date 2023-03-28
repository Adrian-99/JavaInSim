package pl.adrian.api.packets.flags;

import pl.adrian.internal.packets.flags.FlagWithCustomValue;

/**
 * Enumeration for passenger flags.
 */
public enum PassengerFlag implements FlagWithCustomValue {
    /**
     * bit 0, value 1: front, man
     */
    FRONT_MAN(3),
    /**
     * bit 1, value 2: front, woman
     */
    FRONT_WOMAN(3),
    /**
     * bit 2, value 4: rear left, man
     */
    REAR_LEFT_MAN(12),
    /**
     * bit 3, value 8: rear left, woman
     */
    REAR_LEFT_WOMAN(12),
    /**
     * bit 4, value 16: rear middle, man
     */
    REAR_MIDDLE_MAN(48),
    /**
     * bit 5, value 32: rear middle, woman
     */
    REAR_MIDDLE_WOMAN(48),
    /**
     * bit 6, value 64: rear right, man
     */
    REAR_RIGHT_MAN(192),
    /**
     * bit 7, value 128: rear right, woman
     */
    REAR_RIGHT_WOMAN(192);

    private final short mask;

    PassengerFlag(int mask) {
        this.mask = (short) mask;
    }

    @Override
    public int getValue() {
        return 1 << ordinal();
    }

    @Override
    public int getValueMask() {
        return mask;
    }
}
