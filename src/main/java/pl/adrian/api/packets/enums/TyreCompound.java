package pl.adrian.api.packets.enums;

import pl.adrian.internal.packets.enums.EnumHelpers;
import pl.adrian.internal.packets.enums.EnumWithCustomValue;

/**
 * Enumeration for tyre compounds.
 */
public enum TyreCompound implements EnumWithCustomValue {
    /**
     * value 0: slick R1
     */
    R1,
    /**
     * value 1: slick R2
     */
    R2,
    /**
     * value 2: slick R3
     */
    R3,
    /**
     * value 3: slick R4
     */
    R4,
    /**
     * value 4: road super
     */
    ROAD_SUPER,
    /**
     * value 5: road normal
     */
    ROAD_NORMAL,
    /**
     * value 6: hybrid
     */
    HYBRID,
    /**
     * value 7: knobbly
     */
    KNOBBLY,
    /**
     * value 8: ?
     */
    NUM,
    /**
     * value 255: tyre not changed
     */
    NOT_CHANGED;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static TyreCompound fromOrdinal(int ordinal) {
        return ordinal != 255 ? EnumHelpers.get(TyreCompound.class).fromOrdinal(ordinal) : NOT_CHANGED;
    }

    @Override
    public int getValue() {
        return !this.equals(NOT_CHANGED) ? ordinal() : 255;
    }
}
