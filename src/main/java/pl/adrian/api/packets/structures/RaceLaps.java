package pl.adrian.api.packets.structures;

import pl.adrian.api.packets.enums.RaceLapsUnit;
import pl.adrian.internal.packets.util.PacketDataBytes;

/**
 * This class helps to interpret race laps value returned from LFS.
 */
public class RaceLaps {
    private final short value;
    private final RaceLapsUnit unit;

    /**
     * Creates new race laps instance. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public RaceLaps(PacketDataBytes packetDataBytes) {
        var byteValue = packetDataBytes.readByte();
        if (byteValue == 0) {
            value = 0;
            unit = RaceLapsUnit.PRACTICE;
        } else if (byteValue > 0 && byteValue < 100) {
            value = byteValue;
            unit = RaceLapsUnit.LAPS;
        } else if (byteValue > 99 && byteValue < 191) {
            value = (short) ((byteValue - 100) * 10 + 100);
            unit = RaceLapsUnit.LAPS;
        } else if (byteValue > 190 && byteValue < 239) {
            value = (short) (byteValue - 190);
            unit = RaceLapsUnit.HOURS;
        } else {
            throw new IllegalStateException("Invalid byte value for RaceLaps: " + byteValue);
        }
    }

    /**
     * @return race laps unit
     */
    public RaceLapsUnit getUnit() {
        return unit;
    }

    /**
     * @return value of race laps - 0 means it's practice, to check unit for values >0 use
     * {@link #getUnit} method
     */
    public short getValue() {
        return value;
    }
}
