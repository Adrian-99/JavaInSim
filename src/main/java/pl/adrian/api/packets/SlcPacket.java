package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.annotations.CharArray;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.RequestablePacket;

/**
 * SeLected Car. The packet is sent by LFS when a connection selects a car (empty if no car).
 * NOTE: If a new guest joins and does have a car selected then this packet will be sent.
 */
public class SlcPacket extends Packet implements RequestablePacket {
    @Byte
    private final short ucid;
    @CharArray(length = 4)
    private final String cName;

    /**
     * Creates selected car packet.
     * @param reqI 0 unless this is a reply to a {@link pl.adrian.api.packets.enums.TinySubtype#SLC Tiny SLC} request
     * @param ucid connection's unique id (0 = host)
     * @param cName car name
     */
    public SlcPacket(short reqI, short ucid, String cName) {
        super(8, PacketType.SLC, reqI);
        this.ucid = ucid;
        this.cName = cName;
    }

    /**
     * @return connection's unique id (0 = host)
     */
    public short getUcid() {
        return ucid;
    }

    /**
     * @return car name
     */
    public String getCName() {
        return cName;
    }
}
