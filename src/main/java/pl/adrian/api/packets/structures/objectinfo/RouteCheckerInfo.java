package pl.adrian.api.packets.structures.objectinfo;

import pl.adrian.api.packets.enums.ObjectType;

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
    RouteCheckerInfo(short x, short y, short zByte, short flags, short routeIndex) {
        super(x, y, zByte, flags, ObjectType.MARSH_ROUTE, routeIndex);
    }

    /**
     * Creates route checker information.
     * @param x X position (1 metre = 16)
     * @param y Y position (1 metre = 16)
     * @param zByte height (1m = 4)
     * @param isFloating whether object is floating
     * @param diameter diameter (2, 4, 6, 8, ..., 62)
     * @param routeIndex route index
     */
    public RouteCheckerInfo(int x, int y, int zByte, boolean isFloating, int diameter, int routeIndex) {
        super(
                (short) x,
                (short) y,
                (short) zByte,
                calculateFlagsValue(isFloating, diameter),
                ObjectType.MARSH_ROUTE,
                (short) (routeIndex - 1)
        );
    }

    /**
     * @return diameter (2, 4, 6, 8, ..., 62)
     */
    public byte getDiameter() {
        return (byte) ((flags >> 1) & 62);
    }

    /**
     * @return route index
     */
    public short getRouteIndex() {
        return (short) (heading + 1);
    }

    private static short calculateFlagsValue(boolean isFloating, int diameter) {
        return (short) (
                floatingBitForFlags(isFloating) |
                        normalizeIntValueForFlags(diameter, 2, 62, 1)
        );
    }
}
