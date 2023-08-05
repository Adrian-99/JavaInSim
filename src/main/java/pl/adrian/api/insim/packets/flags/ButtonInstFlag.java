package pl.adrian.api.insim.packets.flags;

import pl.adrian.api.insim.packets.BtnPacket;
import pl.adrian.internal.common.flags.FlagWithCustomValue;

/**
 * Enumeration for flags used in {@link BtnPacket}.
 */
public enum ButtonInstFlag implements FlagWithCustomValue {
    /**
     * bit 7, value 128: the button is visible in all screens<br>
     * NOTE: You should not use this flag for most buttons. This is a special flag for buttons
     * that really must be on in all screens (including the garage and options screens). You will
     * probably need to confine these buttons to the top or bottom edge of the screen, to avoid
     * overwriting LFS buttons. Most buttons should be defined without this flag, and positioned
     * in the recommended area so LFS can keep a space clear in the main screens.
     */
    ALWAYS_ON(128);

    private final int value;

    ButtonInstFlag(int value) {
        this.value = value;
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
