package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.flags.Flags;
import pl.adrian.api.packets.flags.NcnFlag;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.annotations.CharArray;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.RequestablePacket;

/**
 * New ConN. The packet is sent by LFS when there is a new connection.
 */
public class NcnPacket extends Packet implements RequestablePacket {
    @Byte
    private final short ucid;
    @CharArray(length = 24)
    private final String uName;
    @CharArray(length = 24)
    private final String pName;
    @Byte
    private final boolean isAdmin;
    @Byte
    private final short total;
    @Byte
    private final Flags<NcnFlag> flags;

    /**
     * Creates new connection packet.
     * @param reqI 0 unless this is a reply to an {@link pl.adrian.api.packets.enums.TinySubtype#NCN Tiny NCN} request
     * @param ucid new connection's unique id (0 = host)
     * @param uName username
     * @param pName nickname
     * @param isAdmin whether is an admin
     * @param total number of connections including host
     * @param flags flags
     */
    public NcnPacket(short reqI,
                     short ucid,
                     String uName,
                     String pName,
                     boolean isAdmin,
                     short total,
                     Flags<NcnFlag> flags) {
        super(56, PacketType.NCN, reqI);
        this.ucid = ucid;
        this.uName = uName;
        this.pName = pName;
        this.isAdmin = isAdmin;
        this.total = total;
        this.flags = flags;
    }

    /**
     * @return new connection's unique id (0 = host)
     */
    public short getUcid() {
        return ucid;
    }

    /**
     * @return username
     */
    public String getUName() {
        return uName;
    }

    /**
     * @return nickname
     */
    public String getPName() {
        return pName;
    }

    /**
     * @return whether is an admin
     */
    public boolean isAdmin() {
        return isAdmin;
    }

    /**
     * @return number of connections including host
     */
    public short getTotal() {
        return total;
    }

    /**
     * @return flags
     */
    public Flags<NcnFlag> getFlags() {
        return flags;
    }
}
