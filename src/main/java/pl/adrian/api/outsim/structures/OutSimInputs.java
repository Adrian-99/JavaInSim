package pl.adrian.api.outsim.structures;

import pl.adrian.internal.packets.util.PacketDataBytes;

/**
 * Structure for inputs.
 */
public class OutSimInputs {
    /**
     * Size of the structure.
     */
    public static final int SIZE = 20;

    private final float throttle;
    private final float brake;
    private final float inputSteer;
    private final float clutch;
    private final float handbrake;

    /**
     * Creates structure for inputs. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public OutSimInputs(PacketDataBytes packetDataBytes) {
        throttle = packetDataBytes.readFloat();
        brake = packetDataBytes.readFloat();
        inputSteer = packetDataBytes.readFloat();
        clutch = packetDataBytes.readFloat();
        handbrake = packetDataBytes.readFloat();
    }

    /**
     * @return throttle - 0 to 1
     */
    public float getThrottle() {
        return throttle;
    }

    /**
     * @return brake - 0 to 1
     */
    public float getBrake() {
        return brake;
    }

    /**
     * @return steering - radians
     */
    public float getInputSteer() {
        return inputSteer;
    }

    /**
     * @return clutch - 0 to 1
     */
    public float getClutch() {
        return clutch;
    }

    /**
     * @return handbrake - 0 to 1
     */
    public float getHandbrake() {
        return handbrake;
    }
}
