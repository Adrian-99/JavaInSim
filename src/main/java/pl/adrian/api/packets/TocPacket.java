package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.base.InfoPacket;
import pl.adrian.internal.packets.base.Packet;

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
     * Creates take over car packet.
     * @param plid player's unique id
     * @param oldUcid old connection's unique id
     * @param newUcid new connection's unique id
     */
    public TocPacket(short plid, short oldUcid, short newUcid) {
        super(8, PacketType.TOC, 0);
        this.plid = plid;
        this.oldUcid = oldUcid;
        this.newUcid = newUcid;
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
