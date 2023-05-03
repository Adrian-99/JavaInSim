package pl.adrian.api.packets.structures.objectinfo;

import pl.adrian.api.packets.enums.ConcreteObjectColour;
import pl.adrian.api.packets.enums.ObjectType;

/**
 * This class holds information about concrete slab wall object.
 */
public class ConcreteSlabWallInfo extends ConcreteObjectInfo {
    /**
     * Creates concrete slab wall object information. Constructor used only internally.
     * @param x X position (1 metre = 16)
     * @param y Y position (1 metre = 16)
     * @param zByte height (1m = 4)
     * @param flags object flags
     * @param heading heading
     */
    ConcreteSlabWallInfo(short x, short y, short zByte, short flags, short heading) {
        super(x, y, zByte, flags, ObjectType.CONCRETE_SLAB_WALL, heading);
    }

    /**
     * Creates concrete slab wall object information.
     * @param x X position (1 metre = 16)
     * @param y Y position (1 metre = 16)
     * @param zByte height (1m = 4)
     * @param colour colour
     * @param length length (2, 4, 8, 16)
     * @param pitch pitch (0 to 90 in steps of 6 degrees)
     * @param heading heading
     */
    public ConcreteSlabWallInfo(int x, int y, int zByte, ConcreteObjectColour colour, int length, int pitch, int heading) {
        super(
                (short) x,
                (short) y,
                (short) zByte,
                (short) (convertColourToFlagsValue(colour) | convertLengthToFlagsValue(length) | convertPitchToFlagsValue(pitch)),
                ObjectType.CONCRETE_SLAB_WALL,
                (short) heading
        );
    }

    /**
     * @return colour
     */
    public ConcreteObjectColour getColour() {
        return getColourInternal();
    }

    /**
     * @return length (2, 4, 8, 16)
     */
    public byte getLength() {
        return getLengthInternal();
    }

    /**
     * @return pitch (0 to 90 in steps of 6 degrees)
     */
    public byte getPitch() {
        return getPitchInternal();
    }
}
