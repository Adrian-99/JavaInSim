package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.flags.ConfirmationFlag;
import pl.adrian.api.packets.flags.Flags;
import pl.adrian.api.packets.flags.PlayerFlag;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.annotations.Unsigned;
import pl.adrian.internal.packets.annotations.Word;
import pl.adrian.internal.packets.base.InfoPacket;
import pl.adrian.internal.packets.base.Packet;

/**
 * FINished race notification. The packet is sent by LFS when any player finishes race
 * (not a final result - use {@link ResPacket}).
 */
public class FinPacket extends Packet implements InfoPacket {
    @Byte
    private final short plid;
    @Unsigned
    private final long tTime;
    @Unsigned
    private final long bTime;
    @Byte
    private final short numStops;
    @Byte
    private final Flags<ConfirmationFlag> confirm;
    @Word
    private final int lapsDone;
    @Word
    private final Flags<PlayerFlag> flags;

    /**
     * Creates finished race notification packet.
     * @param plid player's unique id (0 = player left before result was sent)
     * @param tTime race time (ms)
     * @param bTime best lap (ms)
     * @param numStops number of pit stops
     * @param confirm confirmation flags
     * @param lapsDone laps completed
     * @param flags player flags
     */
    public FinPacket(short plid,
                     long tTime,
                     long bTime,
                     short numStops,
                     Flags<ConfirmationFlag> confirm,
                     int lapsDone,
                     Flags<PlayerFlag> flags) {
        super(20, PacketType.FIN, 0);
        this.plid = plid;
        this.tTime = tTime;
        this.bTime = bTime;
        this.numStops = numStops;
        this.confirm = confirm;
        this.lapsDone = lapsDone;
        this.flags = flags;
    }

    /**
     * @return player's unique id (0 = player left before result was sent)
     */
    public short getPlid() {
        return plid;
    }

    /**
     * @return race time (ms)
     */
    public long getTTime() {
        return tTime;
    }

    /**
     * @return best lap (ms)
     */
    public long getBTime() {
        return bTime;
    }

    /**
     * @return number of pit stops
     */
    public short getNumStops() {
        return numStops;
    }

    /**
     * @return confirmation flags
     */
    public Flags<ConfirmationFlag> getConfirm() {
        return confirm;
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
}
