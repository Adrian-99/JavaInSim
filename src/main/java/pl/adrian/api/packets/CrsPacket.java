package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.base.InfoPacket;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.util.PacketDataBytes;

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
