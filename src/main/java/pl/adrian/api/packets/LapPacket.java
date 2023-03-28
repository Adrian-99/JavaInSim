package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.enums.PenaltyValue;
import pl.adrian.api.packets.flags.Flags;
import pl.adrian.api.packets.flags.PlayerFlag;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.annotations.Unsigned;
import pl.adrian.internal.packets.annotations.Word;
import pl.adrian.internal.packets.base.InfoPacket;
import pl.adrian.internal.packets.base.Packet;

/**
 * LAP time. The packet is sent by LFS when any player finishes a lap.
 */
public class LapPacket extends Packet implements InfoPacket {
    @Byte
    private final short plid;
    @Unsigned
    private final long lTime;
    @Unsigned
    private final long eTime;
    @Word
    private final int lapsDone;
    @Word
    private final Flags<PlayerFlag> flags;
    @Byte
    private final PenaltyValue penalty;
    @Byte
    private final short numStops;
    @Byte
    private final short fuel200;

    /**
     * Creates lap time packet.
     * @param plid player's unique id
     * @param lTime lap time (ms)
     * @param eTime total time (ms)
     * @param lapsDone laps completed
     * @param flags player flags
     * @param penalty current penalty value
     * @param numStops number of pit stops
     * @param fuel200 double fuel percent (if /showfuel yes, 255 otherwise)
     */
    @SuppressWarnings("java:S107")
    public LapPacket(short plid,
                     long lTime,
                     long eTime,
                     int lapsDone,
                     Flags<PlayerFlag> flags,
                     PenaltyValue penalty,
                     short numStops,
                     short fuel200) {
        super(20, PacketType.LAP, 0);
        this.plid = plid;
        this.lTime = lTime;
        this.eTime = eTime;
        this.lapsDone = lapsDone;
        this.flags = flags;
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
     * @return lap time (ms)
     */
    public long getLTime() {
        return lTime;
    }

    /**
     * @return total time (ms)
     */
    public long getETime() {
        return eTime;
    }

    /**
     * @return laps completed
     */
    public int getLapsDone() {
        return lapsDone;
    }

    /**
     * @return player flags
     */
    public Flags<PlayerFlag> getFlags() {
        return flags;
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
