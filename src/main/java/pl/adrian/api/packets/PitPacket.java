package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.enums.PenaltyValue;
import pl.adrian.api.packets.flags.Flags;
import pl.adrian.api.packets.flags.PitWorkFlag;
import pl.adrian.api.packets.flags.PlayerFlag;
import pl.adrian.api.packets.structures.Tyres;
import pl.adrian.internal.packets.annotations.Array;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.annotations.Unsigned;
import pl.adrian.internal.packets.annotations.Word;
import pl.adrian.internal.packets.base.InfoPacket;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.util.PacketDataBytes;

/**
 * PIT stop. The packet is sent by LFS when any player stops at pit garage.
 */
public class PitPacket extends Packet implements InfoPacket {
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
