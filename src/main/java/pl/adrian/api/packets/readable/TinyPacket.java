package pl.adrian.api.packets.readable;

import pl.adrian.api.packets.enums.TinySubtype;
import pl.adrian.internal.packets.base.ReadablePacket;
import pl.adrian.internal.packets.bothways.AbstractTinyPacket;
import pl.adrian.internal.packets.util.PacketReader;

public class TinyPacket extends AbstractTinyPacket implements ReadablePacket {
    public TinyPacket(int reqI) {
        super(reqI);
    }

    @Override
    public ReadablePacket readDataBytes(PacketReader packetReader) {
        subT = TinySubtype.fromOrdinal(packetReader.readByte());

        return this;
    }

    public TinySubtype getSubT() {
        return subT;
    }
}
