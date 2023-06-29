package pl.adrian.api.insim.packets;

import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.internal.insim.packets.annotations.Array;
import pl.adrian.internal.insim.packets.annotations.Char;
import pl.adrian.internal.insim.packets.base.Packet;
import pl.adrian.internal.insim.packets.base.InstructionPacket;
import pl.adrian.internal.insim.packets.exceptions.PacketValidationException;
import pl.adrian.internal.insim.packets.util.PacketBuilder;
import pl.adrian.internal.insim.packets.util.PacketValidator;

/**
 * MSg eXtended - like {@link MstPacket} but longer (not for commands).
 */
public class MsxPacket extends Packet implements InstructionPacket {
    @Char
    @Array(length = 96)
    private final String msg;

    /**
     * Creates msg extended packet.
     * @param msg message to send (max 95 characters)
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public MsxPacket(String msg) throws PacketValidationException {
        super(100, PacketType.MSX, 0);
        this.msg = msg;
        PacketValidator.validate(this);
    }

    @Override
    public byte[] getBytes() {
        return new PacketBuilder(size, type, reqI)
                .writeZeroByte()
                .writeCharArray(msg, 96, false)
                .getBytes();
    }
}
