package pl.adrian.api.packets;

import pl.adrian.internal.packets.annotations.CharArray;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.base.SendablePacket;
import pl.adrian.internal.packets.exceptions.PacketValidationException;
import pl.adrian.internal.packets.util.PacketBuilder;
import pl.adrian.internal.packets.util.PacketValidator;

/**
 * MSg Type - send to LFS to type message or command.
 */
public class MstPacket extends Packet implements SendablePacket {
    @CharArray(length = 64)
    private final String msg;

    /**
     * Creates MSg Type packet
     * @param msg message to be sent
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
                .writeCharArray(msg, 64)
                .getBytes();
    }
}
