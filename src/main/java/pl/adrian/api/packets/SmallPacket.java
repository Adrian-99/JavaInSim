package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.enums.SmallSubtype;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.annotations.Unsigned;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.ReadablePacket;
import pl.adrian.internal.packets.base.SendablePacket;
import pl.adrian.internal.packets.util.PacketBuilder;
import pl.adrian.internal.packets.util.PacketValidator;

public class SmallPacket extends Packet implements SendablePacket, ReadablePacket {
    @Byte
    private final SmallSubtype subT;
    @Unsigned
    private final long uVal;

    public SmallPacket(int reqI, SmallSubtype subT, long uVal) {
        super(8, PacketType.SMALL, reqI);
        this.subT = subT;
        this.uVal = uVal;
        PacketValidator.validate(this);
    }

    @Override
    public byte[] getBytes() {
        return new PacketBuilder(size, type, reqI)
                .writeByte(subT.ordinal())
                .writeUnsigned(uVal)
                .getBytes();
    }

    public SmallSubtype getSubT() {
        return subT;
    }

    public long getUVal() {
        return uVal;
    }
}
