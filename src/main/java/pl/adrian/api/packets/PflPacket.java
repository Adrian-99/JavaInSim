package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.flags.Flags;
import pl.adrian.api.packets.flags.PlayerFlag;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.annotations.Word;
import pl.adrian.internal.packets.base.InfoPacket;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.util.PacketDataBytes;

/**
 * Player FLags. The packet is sent by LFS when flags of any player change.
 */
public class PflPacket extends Packet implements InfoPacket {
    @Byte
    private final short plid;
    @Word
    private final Flags<PlayerFlag> flags;

    /**
     * Creates player flags packet. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public PflPacket(PacketDataBytes packetDataBytes) {
        super(8, PacketType.PFL, 0);
        plid = packetDataBytes.readByte();
        flags = new Flags<>(PlayerFlag.class, packetDataBytes.readWord());
        packetDataBytes.skipZeroBytes(2);
    }

    /**
     * @return player's unique id
     */
    public short getPlid() {
        return plid;
    }

    /**
     * @return player flags
     */
    public Flags<PlayerFlag> getFlags() {
        return flags;
    }
}
