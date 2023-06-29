package pl.adrian.api.insim.packets;

import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.internal.insim.packets.annotations.Byte;
import pl.adrian.internal.insim.packets.base.InfoPacket;
import pl.adrian.internal.insim.packets.base.Packet;
import pl.adrian.internal.common.util.PacketDataBytes;

/**
 * Car ReSet. The packet is sent by LFS when player resets their car.
 */
public class CrsPacket extends Packet implements InfoPacket {
    @Byte
    private final short plid;

    /**
     * Creates car reset packet. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public CrsPacket(PacketDataBytes packetDataBytes) {
        super(4, PacketType.CRS, 0);
        plid = packetDataBytes.readByte();
    }

    /**
     * @return player's unique id
     */
    public short getPlid() {
        return plid;
    }
}
