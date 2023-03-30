package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.enums.PenaltyReason;
import pl.adrian.api.packets.enums.PenaltyValue;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.base.InfoPacket;
import pl.adrian.internal.packets.base.Packet;

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
     * Creates penalty packet.
     * @param plid player's unique id
     * @param oldPen old penalty value
     * @param newPen new penalty value
     * @param reason penalty reason
     */
    public PenPacket(short plid, PenaltyValue oldPen, PenaltyValue newPen, PenaltyReason reason) {
        super(8, PacketType.PEN, 0);
        this.plid = plid;
        this.oldPen = oldPen;
        this.newPen = newPen;
        this.reason = reason;
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
