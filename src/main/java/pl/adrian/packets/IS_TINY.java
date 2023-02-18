package pl.adrian.packets;

import pl.adrian.packets.annotations.Byte;
import pl.adrian.packets.base.Packet;
import pl.adrian.packets.base.ReadablePacket;
import pl.adrian.packets.base.SendablePacket;
import pl.adrian.packets.enums.PacketType;
import pl.adrian.packets.enums.TinySubtype;
import pl.adrian.packets.util.PacketBuilder;
import pl.adrian.packets.util.PacketReader;

public class IS_TINY extends Packet implements SendablePacket, ReadablePacket {
    @Byte
    private TinySubtype subT;

    public IS_TINY(int reqI, TinySubtype subT) {
        super(4, PacketType.ISP_TINY, reqI);
        this.subT = subT;
    }

    public IS_TINY(int reqI) {
        super(4, PacketType.ISP_TINY, reqI);
    }

    @Override
    public ReadablePacket readDataBytes(PacketReader packetReader) {
        subT = TinySubtype.fromOrdinal(packetReader.readByte());

        return this;
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
