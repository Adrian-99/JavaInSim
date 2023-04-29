package pl.adrian.api.packets.structures.objectinfo;

import pl.adrian.api.packets.enums.SpecialControlObjectType;
import pl.adrian.api.packets.enums.ObjectType;

/**
 * This class hold information about special control object.
 */
public class SpecialControlObjectInfo extends ObjectInfo {
    /**
     * Creates control object information. Constructor used only internally.
     * @param x X position (1 metre = 16)
     * @param y Y position (1 metre = 16)
     * @param zByte height (1m = 4)
     * @param flags object flags
     * @param heading heading
     */
    SpecialControlObjectInfo(short x, short y, short zByte, short flags, short heading) {
        super(x, y, zByte, flags, ObjectType.NULL, heading);
    }

    /**
     * Creates control object information.
     * @param x X position (1 metre = 16)
     * @param y Y position (1 metre = 16)
     * @param zByte height (1m = 4)
     * @param type type of control object
     * @param halfWidth half width in metres (value ignored for {@link SpecialControlObjectType#START_POSITION})
     * @param heading heading
     */
    public SpecialControlObjectInfo(int x, int y, int zByte, SpecialControlObjectType type, int halfWidth, int heading) {
        super(
                (short) x,
                (short) y,
                (short) zByte,
                calculateFlagsValue((short) zByte, type, (byte) halfWidth),
                ObjectType.NULL,
                (short) heading
        );
    }

    /**
     * @return type of control object
     */
    public SpecialControlObjectType getType() {
        var typeOrdinal = flags & 3;
        if (getWidth() > 0 || typeOrdinal > 0) {
            return SpecialControlObjectType.fromOrdinal(typeOrdinal);
        } else {
            return SpecialControlObjectType.START_POSITION;
        }
    }

    /**
     * @return width in metres
     */
    public byte getWidth() {
        return (byte) ((flags >> 1) & 62);
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

    private static short calculateFlagsValue(short zByte, SpecialControlObjectType type, byte halfWidth) {
        byte floatingBit = 0;
        if (zByte > 0) {
            floatingBit |= 0x80;
        }
        if (!type.equals(SpecialControlObjectType.START_POSITION)) {
            return (short) (floatingBit | type.ordinal() | (halfWidth & 31) << 2);
        } else {
            return floatingBit;
        }
    }
}
