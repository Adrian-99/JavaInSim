package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.Language;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.annotations.Unsigned;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.RequestablePacket;

/**
 * New Conn Info. The packet is sent by LFS when there is a new connection.
 * Sent on host only if an admin password has been set.
 */
public class NciPacket extends Packet implements RequestablePacket {
    @Byte
    private final short ucid;
    @Byte
    private final Language language;
    @Unsigned
    private final long userId;
    @Unsigned
    private final long ipAddress;

    /**
     * Creates new connection info packet.
     * @param reqI 0 unless this is a reply to a {@link pl.adrian.api.packets.enums.TinySubtype#NCI Tiny NCI} request
     * @param ucid connection's unique id (0 = host)
     * @param language language
     * @param userId LFS UserID
     * @param ipAddress IP address
     */
    public NciPacket(short reqI, short ucid, Language language, long userId, long ipAddress) {
        super(16, PacketType.NCI, reqI);
        this.ucid = ucid;
        this.language = language;
        this.userId = userId;
        this.ipAddress = ipAddress;
    }

    /**
     * @return connection's unique id (0 = host)
     */
    public short getUcid() {
        return ucid;
    }

    /**
     * @return language
     */
    public Language getLanguage() {
        return language;
    }

    /**
     * @return LFS UserID
     */
    public long getUserId() {
        return userId;
    }

    /**
     * @return IP address
     */
    public long getIpAddress() {
        return ipAddress;
    }
}
