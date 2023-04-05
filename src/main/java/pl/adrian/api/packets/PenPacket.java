package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.enums.PenaltyReason;
import pl.adrian.api.packets.enums.PenaltyValue;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.base.InfoPacket;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.util.PacketDataBytes;

/**
 * PENalty. The packet is sent by LFS when any player is given or has cleared penalty.
 */
public class PenPacket extends Packet implements InfoPacket {
    @Byte
    private final short plid;
    @Byte
    private final PenaltyValue oldPen;
    @Byte
    private final PenaltyValue newPen;
    @Byte
    private final PenaltyReason reason;

    /**
     * Creates penalty packet. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public PenPacket(PacketDataBytes packetDataBytes) {
        super(8, PacketType.PEN, 0);
        plid = packetDataBytes.readByte();
        oldPen = PenaltyValue.fromOrdinal(packetDataBytes.readByte());
        newPen = PenaltyValue.fromOrdinal(packetDataBytes.readByte());
        reason = PenaltyReason.fromOrdinal(packetDataBytes.readByte());
        packetDataBytes.skipZeroByte();
    }

    /**
     * @return player's unique id
     */
    public short getPlid() {
        return plid;
    }

    /**
     * @return old penalty value
     */
    public PenaltyValue getOldPen() {
        return oldPen;
    }

    /**
     * @return new penalty value
     */
    public PenaltyValue getNewPen() {
        return newPen;
    }

    /**
     * @return penalty reason
     */
    public PenaltyReason getReason() {
        return reason;
    }
}
