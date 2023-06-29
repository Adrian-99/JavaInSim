package pl.adrian.api.insim.packets.flags;

import pl.adrian.internal.common.flags.FlagWithCustomValue;

/**
 * Flags used in {@link pl.adrian.api.insim.packets.SmallPacket SmallPacket}.
 */
public enum LcsFlag implements FlagWithCustomValue {
    /**
     * signals off
     */
    SIGNALS_OFF(1, 769),
    /**
     * signals left
     */
    SIGNALS_LEFT(257, 769),
    /**
     * signals right
     */
    SIGNALS_RIGHT(513, 769),
    /**
     * signals hazard
     */
    SIGNALS_HAZARD(769, 769),
    /**
     * flash off
     */
    FLASH_OFF(2, 1026),
    /**
     * flash on
     */
    FLASH_ON(1026, 1026),
    /**
     * headlights off
     */
    HEADLIGHTS_OFF(4, 2052),
    /**
     * headlights on
     */
    HEADLIGHTS_ON(2052, 2052),
    /**
     * horn off
     */
    HORN_OFF(8, 458760),
    /**
     * horn 1
     */
    HORN_1(65544, 458760),
    /**
     * horn 2
     */
    HORN_2(131080, 458760),
    /**
     * horn 3
     */
    HORN_3(196616, 458760),
    /**
     * horn 4
     */
    HORN_4(262152, 458760),
    /**
     * horn 5
     */
    HORN_5(327688, 458760),
    /**
     * siren off
     */
    SIREN_OFF(16, 3145744),
    /**
     * siren fast
     */
    SIREN_FAST(1048592, 3145744),
    /**
     * siren slow
     */
    SIREN_SLOW(2097168, 3145744);

    private final int value;
    private final int valueMask;

    LcsFlag(int value, int valueMask) {
        this.value = value;
        this.valueMask = valueMask;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public int getValueMask() {
        return valueMask;
    }
}
