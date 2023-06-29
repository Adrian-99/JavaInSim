package pl.adrian.api.insim.packets.flags;

import pl.adrian.internal.common.flags.FlagWithCustomValue;

/**
 * Enumeration for race flags.
 */
public enum RaceFlag implements FlagWithCustomValue {
    /**
     * bit 0, value 1: can vote
     */
    CAN_VOTE(1),
    /**
     * bit 1, value 2: can select
     */
    CAN_SELECT(2),
    /**
     * bit 5, value 32: mid race
     */
    MID_RACE(32),
    /**
     * bit 6, value 64: must pit
     */
    MUST_PIT(64),
    /**
     * bit 7, value 128: can reset
     */
    CAN_RESET(128),
    /**
     * bit 8, value 256: force cockpit view
     */
    FCV(256),
    /**
     * bit 9, value 512: cruise
     */
    CRUISE(512);

    private final short value;

    RaceFlag(int value) {
        this.value = (short) value;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public int getValueMask() {
        return value;
    }
}
