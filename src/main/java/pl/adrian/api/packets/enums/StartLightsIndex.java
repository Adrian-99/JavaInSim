package pl.adrian.api.packets.enums;

import pl.adrian.api.packets.OcoPacket;
import pl.adrian.internal.packets.enums.EnumWithCustomValue;

/**
 * Enumeration for start lights index used in {@link OcoPacket}.
 */
public enum StartLightsIndex implements EnumWithCustomValue {
    /**
     * value 149: overrides temporary start lights in the layout
     */
    START_LIGHTS(149),
    /**
     * value 240: special value to override the main start light system
     */
    INDEX_MAIN(240);

    private final byte value;

    StartLightsIndex(int value) {
        this.value = (byte) value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
