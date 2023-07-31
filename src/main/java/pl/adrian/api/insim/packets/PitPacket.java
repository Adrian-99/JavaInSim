package pl.adrian.api.insim.packets;

import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.api.insim.packets.enums.PenaltyValue;
import pl.adrian.api.common.flags.Flags;
import pl.adrian.api.insim.packets.flags.PitWorkFlag;
import pl.adrian.api.insim.packets.flags.PlayerFlag;
import pl.adrian.api.insim.packets.structures.Tyres;
import pl.adrian.internal.insim.packets.annotations.Array;
import pl.adrian.internal.insim.packets.annotations.Byte;
import pl.adrian.internal.insim.packets.annotations.Unsigned;
import pl.adrian.internal.insim.packets.annotations.Word;
import pl.adrian.internal.insim.packets.base.InfoPacket;
import pl.adrian.internal.insim.packets.base.AbstractPacket;
import pl.adrian.internal.common.util.PacketDataBytes;

/**
 * PIT stop. The packet is sent by LFS when any player stops at pit garage.
 */
public class PitPacket extends AbstractPacket implements InfoPacket {
    @Byte
    private final short plid;
    @Word
    private final int lapsDone;
    @Word
    private final Flags<PlayerFlag> flags;
    @Byte
    private final short fuelAdd;
    @Byte
    private final PenaltyValue penalty;
    @Byte
    private final short numStops;
    @Byte
    @Array(length = 4)
    private final Tyres tyres;
    @Unsigned
    private final Flags<PitWorkFlag> work;

    /**
     * Creates pit stop packet. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public PitPacket(PacketDataBytes packetDataBytes) {
        super(24, PacketType.PIT, 0);
        plid = packetDataBytes.readByte();
        lapsDone = packetDataBytes.readWord();
        flags = new Flags<>(PlayerFlag.class, packetDataBytes.readWord());
        fuelAdd = packetDataBytes.readByte();
        penalty = PenaltyValue.fromOrdinal(packetDataBytes.readByte());
        numStops = packetDataBytes.readByte();
        packetDataBytes.skipZeroByte();
        tyres = new Tyres(packetDataBytes);
        work = new Flags<>(PitWorkFlag.class, packetDataBytes.readUnsigned());
        packetDataBytes.skipZeroBytes(4);
    }

    /**
     * @return player's unique id
     */
    public short getPlid() {
        return plid;
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
     * @return fuel added percent (if /showfuel yes, 255 otherwise)
     */
    public short getFuelAdd() {
        return fuelAdd;
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
     * @return tyres changed
     */
    public Tyres getTyres() {
        return tyres;
    }

    /**
     * @return pit work
     */
    public Flags<PitWorkFlag> getWork() {
        return work;
    }
}
