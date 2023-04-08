package pl.adrian.api.packets.structures;

import pl.adrian.api.packets.enums.ObjectType;
import pl.adrian.internal.packets.structures.ObjectInfo;

/**
 * This class hold information about autocross object.
 */
public class AutocrossObjectInfo extends ObjectInfo {
    /**
     * Creates autocross object information. Constructor used only internally.
     * @param x X position (1 metre = 16)
     * @param y Y position (1 metre = 16)
     * @param zByte height (1m = 4)
     * @param flags object flags
     * @param index object index
     * @param heading heading
     */
    public AutocrossObjectInfo(short x, short y, short zByte, short flags, short index, short heading) {
        super(x, y, zByte, (short) (0x7F & flags), (short) (0x3F & index), heading);
    }

    /**
     * Creates autocross object information.
     * @param x X position (1 metre = 16)
     * @param y Y position (1 metre = 16)
     * @param zByte height (1m = 4)
     * @param colour colour - only used for chalk (0-3) and tyres (0-5)
     * @param index object index
     * @param heading heading
     */
    public AutocrossObjectInfo(int x, int y, int zByte, int colour, ObjectType index, int heading) {
        super(
                (short) x,
                (short) y,
                (short) zByte,
                (short) (colour & 7),
                (short) index.getValue(),
                (short) heading
        );
    }

    /**
     * @return colour - only used for chalk (0-3) and tyres (0-5)
     */
    public byte getColour() {
        return (byte) (flags & 7);
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
