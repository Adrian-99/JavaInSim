package pl.adrian.api.insim.packets;

import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.api.insim.packets.enums.PitLaneFact;
import pl.adrian.internal.insim.packets.annotations.Byte;
import pl.adrian.internal.insim.packets.base.InfoPacket;
import pl.adrian.internal.insim.packets.base.AbstractPacket;
import pl.adrian.internal.common.util.PacketDataBytes;

/**
 * Pit LAne. The packet is sent by LFS when any player enters or leaves pit lane.
 */
public class PlaPacket extends AbstractPacket implements InfoPacket {
    @Byte
    private final short plid;
    @Byte
    private final PitLaneFact fact;

    /**
     * Creates pit lane packet. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public PlaPacket(PacketDataBytes packetDataBytes) {
        super(8, PacketType.PLA, 0);
        plid = packetDataBytes.readByte();
        fact = PitLaneFact.fromOrdinal(packetDataBytes.readByte());
        packetDataBytes.skipZeroBytes(3);
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
