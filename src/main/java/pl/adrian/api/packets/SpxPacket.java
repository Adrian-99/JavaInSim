package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.enums.PenaltyValue;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.annotations.Unsigned;
import pl.adrian.internal.packets.base.InfoPacket;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.util.PacketDataBytes;

/**
 * SPlit X time. The packet is sent by LFS when any player reaches split time.
 */
public class SpxPacket extends Packet implements InfoPacket {
    @Byte
    private final short plid;
    @Unsigned
    private final long sTime;
    @Unsigned
    private final long eTime;
    @Byte
    private final short split;
    @Byte
    private final PenaltyValue penalty;
    @Byte
    private final short numStops;
    @Byte
    private final short fuel200;

    /**
     * Creates split x time packet. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public SpxPacket(PacketDataBytes packetDataBytes) {
        super(16, PacketType.SPX, 0);
        plid = packetDataBytes.readByte();
        sTime = packetDataBytes.readUnsigned();
        eTime = packetDataBytes.readUnsigned();
        split = packetDataBytes.readByte();
        penalty = PenaltyValue.fromOrdinal(packetDataBytes.readByte());
        numStops = packetDataBytes.readByte();
        fuel200 = packetDataBytes.readByte();
    }

    /**
     * @return player's unique id
     */
    public short getPlid() {
        return plid;
    }

    /**
     * @return split time (ms)
     */
    public long getSTime() {
        return sTime;
    }

    /**
     * @return total time (ms)
     */
    public long getETime() {
        return eTime;
    }

    /**
     * @return split number 1, 2, 3
     */
    public short getSplit() {
        return split;
    }

    /**
     * @return current penalty value
     */
    public PenaltyValue getPenalty() {
        return penalty;
    }

    /**
     * @return number of pit stops
     */
    public short getNumStops() {
        return numStops;
    }

    /**
     * @return double fuel percent (if /showfuel yes, 255 otherwise)
     */
    public short getFuel200() {
        return fuel200;
    }
}
