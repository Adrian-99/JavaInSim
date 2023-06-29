package pl.adrian.api.outsim.structures;

import pl.adrian.internal.common.util.PacketDataBytes;

/**
 * Structure for extra 1.
 */
public class OutSimExtra1 {
    /**
     * Size of the structure.
     */
    public static final int SIZE = 8;

    private final float steerTorque;

    /**
     * Creates structure for extra 1. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public OutSimExtra1(PacketDataBytes packetDataBytes) {
        steerTorque = packetDataBytes.readFloat();
        packetDataBytes.skipZeroBytes(4);
    }

    /**
     * @return Nm : steering torque on front wheels (proportional to force feedback)
     */
    public float getSteerTorque() {
        return steerTorque;
    }
}
