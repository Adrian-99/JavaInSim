package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.base.InfoPacket;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.util.PacketDataBytes;

/**
 * AutoX Object. The packet is sent by LFS when an autocross object is hit (2 second time penalty).
 */
public class AxoPacket extends Packet implements InfoPacket {
    @Byte
    private final short plid;

    /**
     * Creates autoX object packet. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public AxoPacket(PacketDataBytes packetDataBytes) {
        super(4, PacketType.AXO, 0);
        plid = packetDataBytes.readByte();
    }

    /**
     * @return player's unique id
     */
    public short getPlid() {
        return plid;
    }
}
