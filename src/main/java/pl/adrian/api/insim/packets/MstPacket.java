package pl.adrian.api.insim.packets;

import pl.adrian.internal.insim.packets.annotations.Array;
import pl.adrian.internal.insim.packets.annotations.Char;
import pl.adrian.internal.insim.packets.base.AbstractPacket;
import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.internal.insim.packets.base.InstructionPacket;
import pl.adrian.internal.insim.packets.exceptions.PacketValidationException;
import pl.adrian.internal.insim.packets.util.PacketBuilder;
import pl.adrian.internal.insim.packets.util.PacketValidator;

/**
 * MSg Type - send to LFS to type message or command.
 */
public class MstPacket extends AbstractPacket implements InstructionPacket {
    @Char
    @Array(length = 64)
    private final String msg;

    /**
     * Creates MSg Type packet.
     * @param msg message to sent (max 63 characters)
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public MstPacket(String msg) throws PacketValidationException {
        super(68, PacketType.MST, 0);
        this.msg = msg;
        PacketValidator.validate(this);
    }

    @Override
    public byte[] getBytes() {
        return new PacketBuilder(size, type, reqI)
                .writeZeroByte()
                .writeCharArray(msg, 64, false)
                .getBytes();
    }
}
