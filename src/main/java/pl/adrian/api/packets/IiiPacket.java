package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.annotations.CharArray;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.InfoPacket;
import pl.adrian.internal.packets.util.PacketUtils;

/**
 * InsIm Info - /i message from user to host's InSim - variable size.
 */
public class IiiPacket extends Packet implements InfoPacket {
    @Byte
    private final short ucid;
    @Byte
    private final short plid;
    @CharArray(length = 64)
    private final String msg;

    /**
     * Creates InSim info packet.
     * @param ucid connection's unique id (0 = host)
     * @param plid player's unique id (if zero, use ucid)
     * @param msg message
     */
    public IiiPacket(short ucid, short plid, String msg) {
        super(8 + PacketUtils.getLfsCharArraySize(msg, 64), PacketType.III, 0);
        this.ucid = ucid;
        this.plid = plid;
        this.msg = msg;
    }

    /**
     * @return connection's unique id (0 = host)
     */
    public short getUcid() {
        return ucid;
    }

    /**
     * @return player's unique id (if zero, use {@link #getUcid})
     */
    public short getPlid() {
        return plid;
    }

    /**
     * @return message
     */
    public String getMsg() {
        return msg;
    }
}
