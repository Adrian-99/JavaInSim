package pl.adrian.api.insim.packets.structures.objectinfo;

import pl.adrian.api.insim.packets.enums.ObjectType;

/**
 * This class holds information about unknown object.
 */
public class UnknownObjectInfo extends ObjectInfo {
    UnknownObjectInfo(short x, short y, short zByte, short flags, short heading) {
        super(x, y, zByte, flags, ObjectType.NULL, heading);
    }

    /**
     * @return object flags
     */
    public short getFlags() {
        return flags;
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
