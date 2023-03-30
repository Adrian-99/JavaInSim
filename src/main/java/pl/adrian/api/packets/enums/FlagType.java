package pl.adrian.api.packets.enums;

import pl.adrian.internal.packets.enums.EnumHelpers;
import pl.adrian.internal.packets.enums.EnumWithCustomValue;

/**
 * Enumeration for flag types.
 */
public enum FlagType implements EnumWithCustomValue {
    /**
     * value 1: given blue flag
     */
    GIVEN_BLUE(1),
    /**
     * value 2: causing yellow flag
     */
    CAUSING_YELLOW(2);

    private final short value;

    FlagType(int value) {
        this.value = (short) value;
    }

    @Override
    public int getValue() {
        return value;
    }

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static FlagType fromOrdinal(int ordinal) {
        return EnumHelpers.get(FlagType.class).fromOrdinal(ordinal);
    }
}
