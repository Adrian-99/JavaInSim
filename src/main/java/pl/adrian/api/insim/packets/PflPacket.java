package pl.adrian.api.insim.packets;

import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.api.common.flags.Flags;
import pl.adrian.api.insim.packets.flags.PlayerFlag;
import pl.adrian.internal.insim.packets.annotations.Byte;
import pl.adrian.internal.insim.packets.annotations.Word;
import pl.adrian.internal.insim.packets.base.InfoPacket;
import pl.adrian.internal.insim.packets.base.AbstractPacket;
import pl.adrian.internal.common.util.PacketDataBytes;

/**
 * Player FLags. The packet is sent by LFS when flags of any player change.
 */
public class PflPacket extends AbstractPacket implements InfoPacket {
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
