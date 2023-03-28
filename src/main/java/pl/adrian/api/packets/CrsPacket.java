package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.base.InfoPacket;
import pl.adrian.internal.packets.base.Packet;

/**
 * Car ReSet. The packet is sent by LFS when player resets their car.
 */
public class CrsPacket extends Packet implements InfoPacket {
    @Byte
    private final short plid;

    /**
     * Creates car reset packet.
     * @param plid player's unique id
     */
    public CrsPacket(short plid) {
        super(4, PacketType.CRS, 0);
        this.plid = plid;
    }

    /**
     * @return player's unique id
     */
    public short getPlid() {
        return plid;
    }
}
