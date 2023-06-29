package pl.adrian.api.outsim.structures;

import pl.adrian.internal.common.util.PacketDataBytes;

/**
 * Structure containing 3 integers fixed point (1m = 65536).
 */
public class Vec {
    private final int x;
    private final int y;
    private final int z;

    /**
     * Creates vec structure. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    Vec(PacketDataBytes packetDataBytes) {
        this.x = packetDataBytes.readInt();
        this.y = packetDataBytes.readInt();
        this.z = packetDataBytes.readInt();
    }

    /**
     * @return X - right (1m = 65536)
     */
    public int getX() {
        return x;
    }

    /**
     * @return Y - forward (1m = 65536)
     */
    public int getY() {
        return y;
    }

    /**
     * @return Z - up (1m = 65536)
     */
    public int getZ() {
        return z;
    }
}
