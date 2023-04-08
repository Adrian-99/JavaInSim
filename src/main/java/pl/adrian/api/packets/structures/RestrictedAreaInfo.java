package pl.adrian.api.packets.structures;

import pl.adrian.api.packets.enums.MarshallType;
import pl.adrian.internal.packets.structures.ObjectInfo;

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
    public RestrictedAreaInfo(short x, short y, short zByte, short flags, short heading) {
        super(x, y, zByte, (short) (0x80 | flags), (short) 255, heading);
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
                (short) (0x80 | marshallType.ordinal() | (radius & 31) << 2),
                (short) 255,
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
     * @return heading
     */
    public short getHeading() {
        return heading;
    }
}
