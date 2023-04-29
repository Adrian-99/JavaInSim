package pl.adrian.api.packets.structures.objectinfo;

import pl.adrian.api.packets.enums.ObjectType;

/**
 * This class holds information about InSim circle.
 */
public class InSimCircleInfo extends ObjectInfo {
    /**
     * Creates InSim circle information. Constructor used only internally.
     * @param x       X position (1 metre = 16)
     * @param y       Y position (1 metre = 16)
     * @param zByte   height (1m = 4)
     * @param flags   object flags
     * @param heading heading
     */
    InSimCircleInfo(short x, short y, short zByte, short flags, short heading) {
        super(x, y, zByte, flags, ObjectType.MARSH_IS_AREA, heading);
    }

    /**
     * @return diameter (2, 4, 6, 8, ..., 62)
     */
    public byte getDiameter() {
        return (byte) ((flags >> 1) & 62);
    }

    /**
     * @return circle index (seen in the layout editor)
     */
    public short getCircleIndex() {
        return heading;
    }
}
