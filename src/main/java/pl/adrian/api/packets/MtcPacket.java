package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.MessageSound;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.annotations.CharArray;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.SendablePacket;
import pl.adrian.internal.packets.util.PacketBuilder;
import pl.adrian.internal.packets.util.PacketUtils;

/**
 * Msg To Connection - hosts only - send to a connection / a player / all.
 */
public class MtcPacket extends Packet implements SendablePacket {
    @Byte
    private final MessageSound sound;
    @Byte
    private final short ucid;
    @Byte
    private final short plid;
    @CharArray(length = 128)
    private final String text;

    /**
     * Creates msg to connection packet.
     * @param sound sound effect
     * @param ucid connection's unique id (0 = host / 255 = all)
     * @param plid player's unique id (if zero, use ucid)
     * @param text text to send (max 127 characters)
     */
    public MtcPacket(MessageSound sound, int ucid, int plid, String text) {
        super(8 + PacketUtils.getLfsCharArraySize(text, 128), PacketType.MTC, 0);
        this.sound = sound;
        this.ucid = (short) ucid;
        this.plid = (short) plid;
        this.text = text;
    }

    @Override
    public byte[] getBytes() {
        return new PacketBuilder(size, type, reqI)
                .writeByte(sound.ordinal())
                .writeByte(ucid)
                .writeByte(plid)
                .writeZeroBytes(2)
                .writeCharArray(text, 128, true)
                .getBytes();
    }
}
