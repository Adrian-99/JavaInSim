package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.enums.TinySubtype;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.ReadablePacket;
import pl.adrian.internal.packets.base.SendablePacket;
import pl.adrian.internal.packets.util.PacketBuilder;
import pl.adrian.internal.packets.util.PacketValidator;

public class TinyPacket extends Packet implements SendablePacket, ReadablePacket {
    @Byte
    private final TinySubtype subT;

    public TinyPacket(int reqI, TinySubtype subT) {
        super(4, PacketType.TINY, reqI);
        this.subT = subT;
        PacketValidator.validate(this);
    }

    @Override
    public byte[] getBytes() {
        return new PacketBuilder(size, type, reqI)
                .writeByte(subT.ordinal())
                .getBytes();
    }

    public TinySubtype getSubT() {
        return subT;
    }
}
