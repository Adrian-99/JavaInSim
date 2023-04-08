package pl.adrian.api.packets.structures;

import pl.adrian.api.packets.enums.LapTimingType;
import pl.adrian.internal.packets.util.PacketDataBytes;

/**
 * This class helps to interpret lap timing info returned from LFS.
 */
public class LapTiming {
    private final LapTimingType type;
    private final short numberOfCheckpoints;

    /**
     * Creates new lap timing instance. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public LapTiming(PacketDataBytes packetDataBytes) {
        var byteValue = packetDataBytes.readByte();
        switch (byteValue & 0xc0) {
            case 0x80 -> type = LapTimingType.CUSTOM;
            case 0xc0 -> type = LapTimingType.NONE;
            default /*0x40*/ -> type = LapTimingType.STANDARD;
        }
        numberOfCheckpoints = (short) (byteValue & 0x03);
    }

    /**
     * @return lap timing type
     */
    public LapTimingType getType() {
        return type;
    }

    /**
     * @return number of checkpoints if lap timing is enabled
     */
    public short getNumberOfCheckpoints() {
        return numberOfCheckpoints;
    }
}
