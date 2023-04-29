package pl.adrian.api.packets.structures.objectinfo;

import pl.adrian.api.packets.enums.ObjectType;

/**
 * This class holds information about autocross object.
 */
public class AutocrossObjectInfo extends ObjectInfo {
    /**
     * Creates autocross object information. Constructor used only internally.
     * @param x       X position (1 metre = 16)
     * @param y       Y position (1 metre = 16)
     * @param zByte   height (1m = 4)
     * @param flags   object flags
     * @param index   object index
     * @param heading heading
     */
    AutocrossObjectInfo(short x, short y, short zByte, short flags, ObjectType index, short heading) {
        super(x, y, zByte, flags, index, heading);
    }

    /**
     * @return heading - 360 degrees in 256 values:<br>
     *  - 128 : heading of zero<br>
     *  - 192 : heading of 90 degrees<br>
     *  - 0   : heading of 180 degrees<br>
     *  - 64  : heading of -90 degrees
     */
    public short getHeading() {
        return heading;
    }
}
