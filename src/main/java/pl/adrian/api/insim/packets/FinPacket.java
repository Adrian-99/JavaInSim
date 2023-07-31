package pl.adrian.api.insim.packets;

import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.api.insim.packets.flags.ConfirmationFlag;
import pl.adrian.api.common.flags.Flags;
import pl.adrian.api.insim.packets.flags.PlayerFlag;
import pl.adrian.internal.insim.packets.annotations.Byte;
import pl.adrian.internal.insim.packets.annotations.Unsigned;
import pl.adrian.internal.insim.packets.annotations.Word;
import pl.adrian.internal.insim.packets.base.InfoPacket;
import pl.adrian.internal.insim.packets.base.AbstractPacket;
import pl.adrian.internal.common.util.PacketDataBytes;

/**
 * FINished race notification. The packet is sent by LFS when any player finishes race
 * (not a final result - use {@link ResPacket}).
 */
public class FinPacket extends AbstractPacket implements InfoPacket {
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
     * Creates finished race notification packet. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public FinPacket(PacketDataBytes packetDataBytes) {
        super(20, PacketType.FIN, 0);
        plid = packetDataBytes.readByte();
        tTime = packetDataBytes.readUnsigned();
        bTime = packetDataBytes.readUnsigned();
        packetDataBytes.skipZeroByte();
        numStops = packetDataBytes.readByte();
        confirm = new Flags<>(ConfirmationFlag.class, packetDataBytes.readByte());
        packetDataBytes.skipZeroByte();
        lapsDone = packetDataBytes.readWord();
        flags = new Flags<>(PlayerFlag.class, packetDataBytes.readWord());
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
