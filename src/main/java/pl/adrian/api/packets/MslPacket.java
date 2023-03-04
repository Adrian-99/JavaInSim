package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.MessageSound;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.annotations.CharArray;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.SendablePacket;
import pl.adrian.internal.packets.util.PacketBuilder;
import pl.adrian.internal.packets.util.PacketValidator;

/**
 * MSg Local - message to appear on local computer only.
 */
public class MslPacket extends Packet implements SendablePacket {
    @Byte
    private final MessageSound sound;
    @CharArray(length = 128)
    private final String msg;

    /**
     * Creates msg local packet.
     * @param sound sound effect
     * @param msg message to send (max 127 characters)
     */
    public MslPacket(MessageSound sound, String msg) {
        super(132, PacketType.MSL, 0);
        this.sound = sound;
        this.msg = msg;
        PacketValidator.validate(this);
    }

    @Override
    public byte[] getBytes() {
        return new PacketBuilder(size, type, reqI)
                .writeByte(sound.ordinal())
                .writeCharArray(msg, 128, false)
                .getBytes();
    }
}
