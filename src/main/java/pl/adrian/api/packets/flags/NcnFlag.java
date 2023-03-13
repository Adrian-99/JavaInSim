package pl.adrian.api.packets.flags;

import pl.adrian.internal.packets.flags.FlagWithCustomValue;

/**
 * Enumeration for flags used in {@link pl.adrian.api.packets.NcnPacket NcnPacket}.
 */
public enum NcnFlag implements FlagWithCustomValue {
    /**
     * bit 2, value 4: remote
     */
    REMOTE(4);

    private final short value;

    NcnFlag(int value) {
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
