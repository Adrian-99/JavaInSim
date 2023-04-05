package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.base.InfoPacket;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.util.PacketDataBytes;

/**
 * PLayer Pits. The packet is sent by LFS when player goes to settings (stays in player list).
 */
public class PlpPacket extends Packet implements InfoPacket {
    @Byte
    private final short plid;

    /**
     * Creates player pits packet. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public PlpPacket(PacketDataBytes packetDataBytes) {
        super(4, PacketType.PLP, 0);
        plid = packetDataBytes.readByte();
    }

    /**
     * @return player's unique id
     */
    public short getPlid() {
        return plid;
    }
}
