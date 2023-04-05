package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.base.InfoPacket;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.util.PacketDataBytes;

/**
 * Take Over Car. The packet is sent by LFS when car take over happens.
 */
public class TocPacket extends Packet implements InfoPacket {
    @Byte
    private final short plid;
    @Byte
    private final short oldUcid;
    @Byte
    private final short newUcid;

    /**
     * Creates take over car packet. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public TocPacket(PacketDataBytes packetDataBytes) {
        super(8, PacketType.TOC, 0);
        plid = packetDataBytes.readByte();
        oldUcid = packetDataBytes.readByte();
        newUcid = packetDataBytes.readByte();
        packetDataBytes.skipZeroBytes(2);
    }

    /**
     * @return player's unique id
     */
    public short getPlid() {
        return plid;
    }

    /**
     * @return old connection's unique id
     */
    public short getOldUcid() {
        return oldUcid;
    }

    /**
     * @return new connection's unique id
     */
    public short getNewUcid() {
        return newUcid;
    }
}
