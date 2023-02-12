package pl.adrian.packets;

import pl.adrian.packets.annotations.CharArray;
import pl.adrian.packets.base.Packet;
import pl.adrian.packets.enums.PacketType;
import pl.adrian.packets.base.SendablePacket;
import pl.adrian.packets.exceptions.PacketValidationException;
import pl.adrian.packets.util.PacketBuilder;
import pl.adrian.packets.util.PacketValidator;

public class IS_MST extends Packet implements SendablePacket {
    @CharArray(maxLength = 64)
    private final String msg;

    public IS_MST(int reqI, String msg) throws PacketValidationException {
        super(68, PacketType.ISP_MST, reqI);
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
