package pl.adrian.packets;

import pl.adrian.packets.annotations.Byte;
import pl.adrian.packets.annotations.Unsigned;
import pl.adrian.packets.base.Packet;
import pl.adrian.packets.base.ReadablePacket;
import pl.adrian.packets.base.SendablePacket;
import pl.adrian.packets.enums.PacketType;
import pl.adrian.packets.enums.SmallSubtype;
import pl.adrian.packets.util.PacketBuilder;
import pl.adrian.packets.util.PacketReader;

public class IS_SMALL extends Packet implements SendablePacket, ReadablePacket {
    @Byte
    private SmallSubtype subT;

    @Unsigned
    private long uVal;

    public IS_SMALL(int reqI, SmallSubtype subT, long uVal) {
        super(8, PacketType.ISP_SMALL, reqI);
        this.subT = subT;
        this.uVal = uVal;
    }

    public IS_SMALL(int reqI) {
        super(8, PacketType.ISP_SMALL, reqI);
    }

    @Override
    public ReadablePacket readDataBytes(PacketReader packetReader) {
        subT = SmallSubtype.fromOrdinal(packetReader.readByte());
        uVal = packetReader.readUnsigned();

        return this;
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
