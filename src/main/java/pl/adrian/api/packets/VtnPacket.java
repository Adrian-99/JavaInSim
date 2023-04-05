package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.enums.VoteAction;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.InfoPacket;
import pl.adrian.internal.packets.util.PacketDataBytes;

/**
 * VoTe Notification. LFS notifies the external program of any votes to restart or qualify.
 */
public class VtnPacket extends Packet implements InfoPacket {
    @Byte
    private final short ucid;
    @Byte
    private final VoteAction action;

    /**
     * Creates vote notification packet. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public VtnPacket(PacketDataBytes packetDataBytes) {
        super(8, PacketType.VTN, 0);
        packetDataBytes.skipZeroByte();
        ucid = packetDataBytes.readByte();
        action = VoteAction.fromOrdinal(packetDataBytes.readByte());
        packetDataBytes.skipZeroBytes(2);
    }

    /**
     * @return connection's unique id
     */
    public short getUcid() {
        return ucid;
    }

    /**
     * @return vote action
     */
    public VoteAction getAction() {
        return action;
    }
}
