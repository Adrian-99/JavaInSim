package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.enums.PenaltyValue;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.annotations.Unsigned;
import pl.adrian.internal.packets.base.InfoPacket;
import pl.adrian.internal.packets.base.Packet;

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
     * Creates split x time packet.
     * @param plid player's unique id
     * @param sTime split time (ms)
     * @param eTime total time (ms)
     * @param split split number 1, 2, 3
     * @param penalty current penalty value
     * @param numStops number of pit stops
     * @param fuel200 double fuel percent (if /showfuel yes, 255 otherwise)
     */
    public SpxPacket(short plid,
                     long sTime,
                     long eTime,
                     short split,
                     PenaltyValue penalty,
                     short numStops,
                     short fuel200) {
        super(16, PacketType.SPX, 0);
        this.plid = plid;
        this.sTime = sTime;
        this.eTime = eTime;
        this.split = split;
        this.penalty = penalty;
        this.numStops = numStops;
        this.fuel200 = fuel200;
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
