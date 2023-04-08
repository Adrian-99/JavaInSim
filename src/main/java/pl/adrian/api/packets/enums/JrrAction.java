package pl.adrian.api.packets.enums;

import pl.adrian.internal.packets.enums.EnumWithCustomValue;

/**
 * Enumeration for action byte used in {@link pl.adrian.api.packets.JrrPacket JrrPacket}.
 */
public enum JrrAction implements EnumWithCustomValue {
    /**
     * value 0: reject
     */
    REJECT(0),
    /**
     * value 1: allow
     */
    SPAWN(1),
    /**
     * value 4: reset
     */
    RESET(4),
    /**
     * value 5: reset without repair
     */
    RESET_NO_REPAIR(5);
    
    private final byte value;

    JrrAction(int value) {
        this.value = (byte) value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
