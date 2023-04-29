package pl.adrian.api.packets.structures.objectinfo;

import pl.adrian.api.packets.enums.ObjectType;

/**
 * This class holds information about concrete ramp object.
 */
public class ConcreteRampInfo extends ConcreteObjectInfo {
    /**
     * Creates concrete ramp object information. Constructor used only internally.
     * @param x       X position (1 metre = 16)
     * @param y       Y position (1 metre = 16)
     * @param zByte   height (1m = 4)
     * @param flags   object flags
     * @param index   object index
     * @param heading heading
     */
    ConcreteRampInfo(short x, short y, short zByte, short flags, ObjectType index, short heading) {
        super(x, y, zByte, flags, index, heading);
    }

    /**
     * @return width (2, 4, 8, 16)
     */
    public byte getWidth() {
        return getWidthInternal();
    }

    /**
     * @return lenbth (2, 4, 8, 16)
     */
    public byte getLength() {
        return getLengthInternal();
    }

    /**
     * @return height (0.25 to 4 in steps of 0.25)
     */
    public float getHeight() {
        return getHeightInternal();
    }
}
