package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.enums.PitLaneFact;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.base.InfoPacket;
import pl.adrian.internal.packets.base.Packet;

/**
 * Pit LAne. The packet is sent by LFS when any player enters or leaves pit lane.
 */
public class PlaPacket extends Packet implements InfoPacket {
    @Byte
    private final short plid;
    @Byte
    private final PitLaneFact fact;

    /**
     * Creates pit lane packet.
     * @param plid player's unique id
     * @param fact pit lane fact
     */
    public PlaPacket(short plid, PitLaneFact fact) {
        super(8, PacketType.PLA, 0);
        this.plid = plid;
        this.fact = fact;
    }

    /**
     * @return player's unique id
     */
    public short getPlid() {
        return plid;
    }

    /**
     * @return pit lane fact
     */
    public PitLaneFact getFact() {
        return fact;
    }
}
