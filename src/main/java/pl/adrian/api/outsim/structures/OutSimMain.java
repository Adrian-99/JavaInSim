package pl.adrian.api.outsim.structures;

import pl.adrian.internal.common.util.PacketDataBytes;

/**
 * Structure for main data - like old OutSimPack but without Time (first element) or ID (last element).
 */
public class OutSimMain {
    /**
     * Size of the structure.
     */
    public static final int SIZE = 60;

    private final Vector angVel;
    private final float heading;
    private final float pitch;
    private final float roll;
    private final Vector accel;
    private final Vector vel;
    private final Vec pos;

    /**
     * Creates structure for main data. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public OutSimMain(PacketDataBytes packetDataBytes) {
        angVel = new Vector(packetDataBytes);
        heading = packetDataBytes.readFloat();
        pitch = packetDataBytes.readFloat();
        roll = packetDataBytes.readFloat();
        accel = new Vector(packetDataBytes);
        vel = new Vector(packetDataBytes);
        pos = new Vec(packetDataBytes);
    }

    /**
     * @return 3 floats, angular velocity vector
     */
    public Vector getAngVel() {
        return angVel;
    }

    /**
     * @return heading - anticlockwise from above (Z)
     */
    public float getHeading() {
        return heading;
    }

    /**
     * @return pitch - anticlockwise from right (X)
     */
    public float getPitch() {
        return pitch;
    }

    /**
     * @return roll - anticlockwise from front (Y)
     */
    public float getRoll() {
        return roll;
    }

    /**
     * @return acceleration - 3 floats X, Y, Z
     */
    public Vector getAccel() {
        return accel;
    }

    /**
     * @return velocity - 3 floats X, Y, Z
     */
    public Vector getVel() {
        return vel;
    }

    /**
     * @return position - 3 ints X, Y, Z (1m = 65536)
     */
    public Vec getPos() {
        return pos;
    }
}
