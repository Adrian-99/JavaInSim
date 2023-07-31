package pl.adrian.api.insim.packets;

import pl.adrian.api.insim.packets.enums.LeaveReason;
import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.internal.insim.packets.annotations.Byte;
import pl.adrian.internal.insim.packets.base.InfoPacket;
import pl.adrian.internal.insim.packets.base.AbstractPacket;
import pl.adrian.internal.common.util.PacketDataBytes;

/**
 * ConN Leave. The packet is sent by LFS when connection leaves.
 */
public class CnlPacket extends AbstractPacket implements InfoPacket {
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
