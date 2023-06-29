package pl.adrian.api.insim.packets;

import pl.adrian.api.insim.packets.enums.MessageType;
import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.internal.insim.packets.annotations.Array;
import pl.adrian.internal.insim.packets.annotations.Byte;
import pl.adrian.internal.insim.packets.annotations.Char;
import pl.adrian.internal.insim.packets.base.Packet;
import pl.adrian.internal.insim.packets.base.InfoPacket;
import pl.adrian.internal.common.util.PacketDataBytes;

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
    @Char
    @Array(length = 128)
    private final String msg;

    /**
     * Creates msg out packet. Constructor used only internally.
     * @param size packet size
     * @param packetDataBytes packet data bytes
     */
    public MsoPacket(short size, PacketDataBytes packetDataBytes) {
        super(size, PacketType.MSO, 0);
        packetDataBytes.skipZeroByte();
        ucid = packetDataBytes.readByte();
        plid = packetDataBytes.readByte();
        userType = MessageType.fromOrdinal(packetDataBytes.readByte());
        textStart = packetDataBytes.readByte();
        msg = packetDataBytes.readCharArray(size - 8);
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
