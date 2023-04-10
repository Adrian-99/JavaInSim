package pl.adrian.api.packets.enums;

import pl.adrian.internal.packets.enums.EnumHelpers;
import pl.adrian.internal.packets.enums.EnumWithCustomValue;

/**
 * Enumeration for HLVC used in {@link pl.adrian.api.packets.HlvPacket HlvPacket}.
 */
public enum Hlvc implements EnumWithCustomValue {
    /**
     * value 0: ground
     */
    GROUND(0),
    /**
     * value 1: wall
     */
    WALL(1),
    /**
     * value 4: speeding
     */
    SPEEDING(4),
    /**
     * value 5: out of bounds
     */
    OUT_OF_BOUNDS(5);

    private final byte value;

    Hlvc(int value) {
        this.value = (byte) value;
    }

    @Override
    public int getValue() {
        return value;
    }

    /**
     * Converts ordinal number into enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static Hlvc fromOrdinal(int ordinal) {
        return EnumHelpers.get(Hlvc.class).fromOrdinal(ordinal);
    }
}
