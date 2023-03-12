package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.MessageType;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.annotations.CharArray;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.InfoPacket;
import pl.adrian.internal.packets.util.PacketUtils;

/**
 * MSg Out - system messages and user messages - variable size.
 */
public class MsoPacket extends Packet implements InfoPacket {
    @Byte
    private final short ucid;
    @Byte
    private final short plid;
    @Byte
    private final MessageType userType;
    @Byte
    private final short textStart;
    @CharArray(length = 128)
    private final String msg;

    /**
     * Creates msg out packet.
     * @param ucid connection's unique id (0 = host)
     * @param plid player's unique id (if zero, use ucid)
     * @param userType message type
     * @param textStart first character of the actual text (after player name)
     * @param msg message
     */
    public MsoPacket(short ucid, short plid, MessageType userType, short textStart, String msg) {
        super(8 + PacketUtils.getLfsCharArraySize(msg, 128), PacketType.MSO, 0);
        this.ucid = ucid;
        this.plid = plid;
        this.userType = userType;
        this.textStart = textStart;
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
     * @return message type
     */
    public MessageType getUserType() {
        return userType;
    }

    /**
     * @return first character of the actual text (after player name)
     */
    public short getTextStart() {
        return textStart;
    }

    /**
     * @return message
     */
    public String getMsg() {
        return msg;
    }
}
