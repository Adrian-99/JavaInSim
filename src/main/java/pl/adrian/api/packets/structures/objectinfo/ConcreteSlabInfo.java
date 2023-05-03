package pl.adrian.api.packets.structures.objectinfo;

import pl.adrian.api.packets.enums.ObjectType;

/**
 * This class holds information about concrete slab object.
 */
public class ConcreteSlabInfo extends ConcreteObjectInfo {
    /**
     * Creates concrete slab object information. Constructor used only internally.
     * @param x X position (1 metre = 16)
     * @param y Y position (1 metre = 16)
     * @param zByte height (1m = 4)
     * @param flags object flags
     * @param heading heading
     */
    ConcreteSlabInfo(short x, short y, short zByte, short flags, short heading) {
        super(x, y, zByte, flags, ObjectType.CONCRETE_SLAB, heading);
    }

    /**
     * Creates concrete slab object information.
     * @param x X position (1 metre = 16)
     * @param y Y position (1 metre = 16)
     * @param zByte height (1m = 4)
     * @param width width (2, 4, 8, 16)
     * @param length length (2, 4, 8, 16)
     * @param pitch pitch (0 to 90 in steps of 6 degrees)
     * @param heading heading
     */
    public ConcreteSlabInfo(int x, int y, int zByte, int width, int length, int pitch, int heading) {
        super(
                (short) x,
                (short) y,
                (short) zByte,
                (short) (convertWidthToFlagsValue(width) | convertLengthToFlagsValue(length) | convertPitchToFlagsValue(pitch)),
                ObjectType.CONCRETE_SLAB,
                (short) heading
        );
    }

    /**
     * @return width (2, 4, 8, 16)
     */
    public byte getWidth() {
        return getWidthInternal();
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
