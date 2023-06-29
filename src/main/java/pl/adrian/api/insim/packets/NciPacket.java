package pl.adrian.api.insim.packets;

import pl.adrian.api.insim.packets.enums.Language;
import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.internal.insim.packets.annotations.Byte;
import pl.adrian.internal.insim.packets.annotations.Unsigned;
import pl.adrian.internal.insim.packets.base.Packet;
import pl.adrian.internal.insim.packets.base.RequestablePacket;
import pl.adrian.internal.common.util.PacketDataBytes;

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
     * Creates new connection info packet. Constructor used only internally.
     * @param reqI 0 unless this is a reply to a {@link pl.adrian.api.insim.packets.enums.TinySubtype#NCI Tiny NCI} request
     * @param packetDataBytes packet data bytes
     */
    public NciPacket(short reqI, PacketDataBytes packetDataBytes) {
        super(16, PacketType.NCI, reqI);
        ucid = packetDataBytes.readByte();
        language = Language.fromOrdinal(packetDataBytes.readByte());
        packetDataBytes.skipZeroBytes(3);
        userId = packetDataBytes.readUnsigned();
        ipAddress = packetDataBytes.readUnsigned();
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
