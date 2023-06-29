package pl.adrian.api.outsim.structures;

import pl.adrian.internal.common.util.PacketDataBytes;

/**
 * Structure for time.
 */
public class OutSimTime {
    /**
     * Size of the structure.
     */
    public static final int SIZE = 4;

    private final long time;

    /**
     * Creates structure for time. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public OutSimTime(PacketDataBytes packetDataBytes) {
        time = packetDataBytes.readUnsigned();
    }

    /**
     * @return time in milliseconds (to check order)
     */
    public long getTime() {
        return time;
    }
}
