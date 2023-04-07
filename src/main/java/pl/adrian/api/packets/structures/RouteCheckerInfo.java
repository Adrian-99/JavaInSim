package pl.adrian.api.packets.structures;

import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.util.PacketBuilder;

/**
 * This class holds information about route checker.
 */
public class RouteCheckerInfo implements ObjectSubtypeInfo {
    @Byte
    private final short flags;
    @Byte
    private final short routeIndex;

    RouteCheckerInfo(short flags, short routeIndex) {
        this.flags = flags;
        this.routeIndex = routeIndex;
    }

    /**
     * Creates route checker information.
     * @param radius radius in meters
     * @param routeIndex route index
     */
    public RouteCheckerInfo(int radius, int routeIndex) {
        flags = (short) ((radius & 31) << 2);
        this.routeIndex = (short) routeIndex;
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
        return routeIndex;
    }

    @Override
    public void appendBytes(PacketBuilder packetBuilder) {
        packetBuilder.writeByte(flags)
                .writeByte(255)
                .writeByte(routeIndex);
    }
}
