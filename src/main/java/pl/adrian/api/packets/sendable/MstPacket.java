package pl.adrian.api.packets.sendable;

import pl.adrian.internal.packets.annotations.CharArray;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.base.SendablePacket;
import pl.adrian.internal.packets.exceptions.PacketValidationException;
import pl.adrian.internal.packets.util.PacketBuilder;
import pl.adrian.internal.packets.util.PacketValidator;

public class MstPacket extends Packet implements SendablePacket {
    @CharArray(maxLength = 64)
    private final String msg;

    public MstPacket(int reqI, String msg) throws PacketValidationException {
        super(68, PacketType.MST, reqI);
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
