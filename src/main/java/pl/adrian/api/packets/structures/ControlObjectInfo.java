package pl.adrian.api.packets.structures;

import pl.adrian.api.packets.enums.ControlObjectType;
import pl.adrian.api.packets.enums.ObjectType;
import pl.adrian.internal.packets.structures.ObjectInfo;

/**
 * This class hold information about control object.
 */
public class ControlObjectInfo extends ObjectInfo {
    /**
     * Creates control object information. Constructor used only internally.
     * @param x X position (1 metre = 16)
     * @param y Y position (1 metre = 16)
     * @param zByte height (1m = 4)
     * @param flags object flags
     * @param index object index
     * @param heading heading
     */
    public ControlObjectInfo(short x, short y, short zByte, short flags, short index, short heading) {
        super(x, y, zByte, (short) (0x80 | flags), (short) (0x3F & index), heading);
    }

    /**
     * Creates control object information.
     * @param x X position (1 metre = 16)
     * @param y Y position (1 metre = 16)
     * @param zByte height (1m = 4)
     * @param type type of control object
     * @param width half width in metres (value ignored for {@link ControlObjectType#START_POSITION})
     * @param index object index
     * @param heading heading
     */
    public ControlObjectInfo(int x, int y, int zByte, ControlObjectType type, int width, ObjectType index, int heading) {
        super(
                (short) x,
                (short) y,
                (short) zByte,
                !type.equals(ControlObjectType.START_POSITION) ? (short) (0x80 | type.ordinal() | (width & 31) << 2) : 0x80,
                (short) index.getValue(),
                (short) heading
        );
    }

    /**
     * @return type of control object
     */
    public ControlObjectType getType() {
        var typeOrdinal = flags & 3;
        if (getWidth() > 0 || typeOrdinal > 0) {
            return ControlObjectType.fromOrdinal(typeOrdinal);
        } else {
            return ControlObjectType.START_POSITION;
        }
    }

    /**
     * @return half width in metres
     */
    public byte getWidth() {
        return (byte) ((flags >> 2) & 31);
    }

    /**
     * @return object index
     */
    public ObjectType getIndex() {
        return ObjectType.fromOrdinal(index);
    }

    /**
     * @return heading
     */
    public short getHeading() {
        return heading;
    }
}
