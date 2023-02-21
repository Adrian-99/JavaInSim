package pl.adrian.internal.packets.bothways;

import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.enums.TinySubtype;

public abstract class AbstractTinyPacket extends Packet {
    @Byte
    protected TinySubtype subT;

    protected AbstractTinyPacket(int reqI, TinySubtype subT) {
        super(4, PacketType.TINY, reqI);
        this.subT = subT;
    }

    protected AbstractTinyPacket(int reqI) {
        super(4, PacketType.TINY, reqI);
    }
}
