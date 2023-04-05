package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.base.InfoPacket;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.util.PacketDataBytes;

/**
 * PLayer Leave race. The packet is sent by LFS when player spectates (removed from player list).
 */
public class PllPacket extends Packet implements InfoPacket {
    @Byte
    private final short plid;

    /**
     * Creates player leave race packet. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public PllPacket(PacketDataBytes packetDataBytes) {
        super(4, PacketType.PLL, 0);
        plid = packetDataBytes.readByte();
    }

    /**
     * @return player's unique id
     */
    public short getPlid() {
        return plid;
    }
}
