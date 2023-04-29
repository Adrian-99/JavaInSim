package pl.adrian.api.packets.structures.objectinfo;

import pl.adrian.api.packets.enums.ObjectType;

/**
 * This class hold information about concrete pillar object.
 */
public class ConcretePillarInfo extends ConcreteObjectInfo {
    /**
     * Creates concrete pillar object information. Constructor used only internally.
     * @param x       X position (1 metre = 16)
     * @param y       Y position (1 metre = 16)
     * @param zByte   height (1m = 4)
     * @param flags   object flags
     * @param index   object index
     * @param heading heading
     */
    ConcretePillarInfo(short x, short y, short zByte, short flags, ObjectType index, short heading) {
        super(x, y, zByte, flags, index, heading);
    }

    /**
     * @return size X (0.25, 0.5, 0.75, 1)
     */
    public float getSizeX() {
        return getSizeXInternal();
    }

    /**
     * @return size Y (0.25, 0.5, 0.75, 1)
     */
    public float getSizeY() {
        return getSizeYInternal();
    }

    /**
     * @return height (0.25 to 4 in steps of 0.25)
     */
    public float getHeight() {
        return getHeightInternal();
    }
}
