package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.LeaveReason;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.base.InfoPacket;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.util.PacketDataBytes;

/**
 * ConN Leave. The packet is sent by LFS when connection leaves.
 */
public class CnlPacket extends Packet implements InfoPacket {
    @Byte
    private final short ucid;
    @Byte
    private final LeaveReason reason;
    @Byte
    private final short total;

    /**
     * Creates conn leave packet. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public CnlPacket(PacketDataBytes packetDataBytes) {
        super(8, PacketType.CNL, 0);
        ucid = packetDataBytes.readByte();
        reason = LeaveReason.fromOrdinal(packetDataBytes.readByte());
        total = packetDataBytes.readByte();
        packetDataBytes.skipZeroBytes(2);
    }

    /**
     * @return unique id of the connection which left
     */
    public short getUcid() {
        return ucid;
    }

    /**
     * @return leave reason
     */
    public LeaveReason getReason() {
        return reason;
    }

    /**
     * @return number of connections including host
     */
    public short getTotal() {
        return total;
    }
}
