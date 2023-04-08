package pl.adrian.api.packets.structures;

import pl.adrian.internal.packets.structures.ObjectInfo;

/**
 * This class holds information about route checker.
 */
public class RouteCheckerInfo extends ObjectInfo {
    /**
     * Creates route checker information. Constructor used only internally.
     * @param x X position (1 metre = 16)
     * @param y Y position (1 metre = 16)
     * @param zByte height (1m = 4)
     * @param flags object flags
     * @param routeIndex route index
     */
    public RouteCheckerInfo(short x, short y, short zByte, short flags, short routeIndex) {
        super(x, y, zByte, (short) (0x7F & flags), (short) 255, routeIndex);
    }

    /**
     * Creates route checker information.
     * @param x X position (1 metre = 16)
     * @param y Y position (1 metre = 16)
     * @param zByte height (1m = 4)
     * @param radius radius in meters
     * @param routeIndex route index
     */
    public RouteCheckerInfo(int x, int y, int zByte, int radius, int routeIndex) {
        super(
                (short) x,
                (short) y,
                (short) zByte,
                (short) ((radius & 31) << 2),
                (short) 255,
                (short) routeIndex
        );
    }

    /**
     * @return radius in meters
     */
    public byte getRadius() {
        return (byte) ((flags >> 2) & 31);
    }

    /**
     * @return route index
     */
    public short getRouteIndex() {
        return heading;
    }
}
