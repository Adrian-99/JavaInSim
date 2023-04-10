package pl.adrian.api.packets.structures;

import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.annotations.Short;
import pl.adrian.internal.packets.util.PacketDataBytes;

/**
 * The class stores information about car in a contact with an object.
 */
public class CarContOBJ {
    @Byte
    private final short direction;
    @Byte
    private final short heading;
    @Byte
    private final short speed;
    @Byte
    private final short zByte;
    @Short
    private final short x;
    @Short
    private final short y;

    /**
     * Creates car in contact with object information. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public CarContOBJ(PacketDataBytes packetDataBytes) {
        direction = packetDataBytes.readByte();
        heading = packetDataBytes.readByte();
        speed = packetDataBytes.readByte();
        zByte = packetDataBytes.readByte();
        x = packetDataBytes.readShort();
        y = packetDataBytes.readShort();
    }

    /**
     * @return car's motion if Speed > 0: 0 = world y direction, 128 = 180 deg
     */
    public short getDirection() {
        return direction;
    }

    /**
     * @return direction of forward axis: 0 = world y direction, 128 = 180 deg
     */
    public short getHeading() {
        return heading;
    }

    /**
     * @return speed (m/s)
     */
    public short getSpeed() {
        return speed;
    }

    /**
     * @return zByte
     */
    public short getZByte() {
        return zByte;
    }

    /**
     * @return X position (1 metre = 16)
     */
    public short getX() {
        return x;
    }

    /**
     * @return Y position (1 metre = 16)
     */
    public short getY() {
        return y;
    }
}
