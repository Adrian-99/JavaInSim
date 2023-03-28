package pl.adrian.api.packets.enums;

import pl.adrian.internal.packets.enums.EnumHelpers;

/**
 * Enumeration for tyre compounds.
 */
public enum TyreCompound {
    /**
     * value 0: spare
     */
    RESERVED,
    /**
     * value 1: slick R2
     */
    SLICK_R2,
    /**
     * value 2: slick R3
     */
    SLICK_R3,
    /**
     * value 3: slick R4
     */
    SLICK_R4,
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
    KNOBBLY;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static TyreCompound fromOrdinal(int ordinal) {
        return EnumHelpers.get(TyreCompound.class).fromOrdinal(ordinal);
    }
}
