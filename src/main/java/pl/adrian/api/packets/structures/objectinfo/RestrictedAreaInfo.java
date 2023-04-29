package pl.adrian.api.packets.structures.objectinfo;

import pl.adrian.api.packets.enums.MarshallType;
import pl.adrian.api.packets.enums.ObjectType;

/**
 * This class hold information about restricted area.
 */
public class RestrictedAreaInfo extends ObjectInfo {
    /**
     * Creates restricted area information. Constructor used only internally.
     * @param x X position (1 metre = 16)
     * @param y Y position (1 metre = 16)
     * @param zByte height (1m = 4)
     * @param flags object flags
     * @param heading heading
     */
    RestrictedAreaInfo(short x, short y, short zByte, short flags, short heading) {
        super(x, y, zByte, flags, ObjectType.MARSH_MARSHAL, heading);
    }

    /**
     * Creates restricted area information.
     * @param x X position (1 metre = 16)
     * @param y Y position (1 metre = 16)
     * @param zByte height (1m = 4)
     * @param marshallType marshall type
     * @param radius radius in meters
     * @param heading heading
     */
    public RestrictedAreaInfo(int x, int y, int zByte, MarshallType marshallType, int radius, int heading) {
        super(
                (short) x,
                (short) y,
                (short) zByte,
                (short) ((zByte > 0 ? 0x80 : 0) | marshallType.ordinal() | (radius & 31) << 2),
                ObjectType.MARSH_MARSHAL,
                (short) heading
        );
    }

    /**
     * @return marshall type
     */
    public MarshallType getMarshallType() {
        return MarshallType.fromOrdinal(flags & 3);
    }

    /**
     * @return radius in meters
     */
    public byte getRadius() {
        return (byte) ((flags >> 2) & 31);
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
