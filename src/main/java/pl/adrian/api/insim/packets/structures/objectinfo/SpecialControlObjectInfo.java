package pl.adrian.api.insim.packets.structures.objectinfo;

import pl.adrian.api.insim.packets.enums.SpecialControlObjectType;
import pl.adrian.api.insim.packets.enums.ObjectType;

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
     * @param isFloating whether object is floating
     * @param type type of control object
     * @param width width in metres (2, 4, 6, 8, ..., 62) (value ignored for {@link SpecialControlObjectType#START_POSITION})
     * @param heading heading
     */
    public SpecialControlObjectInfo(int x,
                                    int y,
                                    int zByte,
                                    boolean isFloating,
                                    SpecialControlObjectType type,
                                    int width,
                                    int heading) {
        super(
                (short) x,
                (short) y,
                (short) zByte,
                calculateFlagsValue(isFloating, type, width),
                ObjectType.NULL,
                (short) heading
        );
    }

    /**
     * @return type of control object
     */
    public SpecialControlObjectType getType() {
        var typeOrdinal = flags & 3;
        return getWidth() > 0 || typeOrdinal > 0 ?
                SpecialControlObjectType.fromOrdinal(typeOrdinal) :
                SpecialControlObjectType.START_POSITION;
    }

    /**
     * @return width in metres (2, 4, 6, 8, ..., 62)
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

    private static short calculateFlagsValue(boolean isFloating, SpecialControlObjectType type, int width) {
        if (!type.equals(SpecialControlObjectType.START_POSITION)) {
            return (short) (
                    floatingBitForFlags(isFloating) |
                            type.ordinal() |
                            normalizeIntValueForFlags(width, 2, 62, 1)
            );
        } else {
            return floatingBitForFlags(isFloating);
        }
    }
}
