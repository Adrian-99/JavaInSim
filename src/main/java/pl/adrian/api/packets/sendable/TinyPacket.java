package pl.adrian.api.packets.sendable;

import pl.adrian.api.packets.enums.TinySubtype;
import pl.adrian.internal.packets.base.SendablePacket;
import pl.adrian.internal.packets.bothways.AbstractTinyPacket;
import pl.adrian.internal.packets.util.PacketBuilder;

public class TinyPacket extends AbstractTinyPacket implements SendablePacket {
    public TinyPacket(int reqI, TinySubtype subT) {
        super(reqI, subT);
    }

    @Override
    public byte[] getBytes() {
        return new PacketBuilder(size, type, reqI)
                .writeByte(subT.ordinal())
                .getBytes();
    }
}
