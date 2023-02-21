package pl.adrian.api.packets.readable;

import pl.adrian.api.packets.enums.SmallSubtype;
import pl.adrian.internal.packets.base.ReadablePacket;
import pl.adrian.internal.packets.bothways.AbstractSmallPacket;
import pl.adrian.internal.packets.util.PacketReader;

public class SmallPacket extends AbstractSmallPacket implements ReadablePacket {
    public SmallPacket(int reqI) {
        super(reqI);
    }

    @Override
    public ReadablePacket readDataBytes(PacketReader packetReader) {
        subT = SmallSubtype.fromOrdinal(packetReader.readByte());
        uVal = packetReader.readUnsigned();

        return this;
    }

    public SmallSubtype getSubT() {
        return subT;
    }

    public long getUVal() {
        return uVal;
    }
}
