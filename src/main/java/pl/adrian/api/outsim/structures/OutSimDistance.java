package pl.adrian.api.outsim.structures;

import pl.adrian.internal.common.util.PacketDataBytes;

/**
 * Structure for distance.
 */
public class OutSimDistance {
    /**
     * Size of the structure.
     */
    public static final int SIZE = 8;

    private final float currentLapDist;
    private final float indexedDistance;

    /**
     * Creates structure for distance. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public OutSimDistance(PacketDataBytes packetDataBytes) {
        currentLapDist = packetDataBytes.readFloat();
        indexedDistance = packetDataBytes.readFloat();
    }

    /**
     * @return current lap distance - m travelled by car
     */
    public float getCurrentLapDist() {
        return currentLapDist;
    }

    /**
     * @return indexed distance - m track ruler measurement
     */
    public float getIndexedDistance() {
        return indexedDistance;
    }
}
