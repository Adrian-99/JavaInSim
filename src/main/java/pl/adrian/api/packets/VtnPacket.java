package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.enums.VoteAction;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.ReadablePacket;

/**
 * VoTe Notification.
 */
public class VtnPacket extends Packet implements ReadablePacket {
    @Byte
    private final short ucid;
    @Byte
    private final VoteAction action;

    /**
     * Creates vote notification packet.
     * @param ucid connection's unique id
     * @param action vote action
     */
    public VtnPacket(short ucid, VoteAction action) {
        super(8, PacketType.VTN, 0);
        this.ucid = ucid;
        this.action = action;
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
