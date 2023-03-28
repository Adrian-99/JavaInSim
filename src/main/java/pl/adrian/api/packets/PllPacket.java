package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.base.InfoPacket;
import pl.adrian.internal.packets.base.Packet;

/**
 * PLayer Leave race. The packet is sent by LFS when player spectates (removed from player list).
 */
public class PllPacket extends Packet implements InfoPacket {
    @Byte
    private final short plid;

    /**
     * Creates player leave race packet.
     * @param plid player's unique id
     */
    public PllPacket(short plid) {
        super(4, PacketType.PLL, 0);
        this.plid = plid;
    }

    /**
     * @return player's unique id
     */
    public short getPlid() {
        return plid;
    }
}
