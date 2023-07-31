package pl.adrian.api.insim.packets;

import pl.adrian.api.insim.packets.enums.MessageSound;
import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.internal.insim.packets.annotations.Array;
import pl.adrian.internal.insim.packets.annotations.Byte;
import pl.adrian.internal.insim.packets.annotations.Char;
import pl.adrian.internal.insim.packets.base.AbstractPacket;
import pl.adrian.internal.insim.packets.base.InstructionPacket;
import pl.adrian.internal.insim.packets.exceptions.PacketValidationException;
import pl.adrian.internal.insim.packets.util.PacketBuilder;
import pl.adrian.internal.insim.packets.util.PacketValidator;

/**
 * MSg Local - message to appear on local computer only.
 */
public class MslPacket extends AbstractPacket implements InstructionPacket {
    @Byte
    private final MessageSound sound;
    @Char
    @Array(length = 128)
    private final String msg;

    /**
     * Creates msg local packet.
     * @param sound sound effect
     * @param msg message to send (max 127 characters)
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public MslPacket(MessageSound sound, String msg) throws PacketValidationException {
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
