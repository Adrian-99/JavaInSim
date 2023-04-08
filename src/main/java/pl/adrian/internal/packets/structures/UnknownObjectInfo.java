package pl.adrian.internal.packets.structures;

/**
 * This class hold information about unknown object.
 */
public class UnknownObjectInfo extends ObjectInfo {
    UnknownObjectInfo(short x, short y, short zByte, short flags, short index, short heading) {
        super(x, y, zByte, flags, (short) (0xC0 | index), heading);
    }

    /**
     * @return object flags
     */
    public short getFlags() {
        return flags;
    }

    /**
     * @return object index
     */
    public short getIndex() {
        return index;
    }

    /**
     * @return heading
     */
    public short getHeading() {
        return heading;
    }
}
