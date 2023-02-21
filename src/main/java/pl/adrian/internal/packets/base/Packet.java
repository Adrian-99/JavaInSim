package pl.adrian.internal.packets.base;

import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.api.packets.enums.PacketType;

public abstract class Packet {
    @Byte
    protected final short size;
    @Byte
    protected final PacketType type;
    @Byte
    protected final short reqI;

    protected Packet(int size, PacketType type, int reqI) {
        this.size = (short) size;
        this.type = type;
        this.reqI = (short) reqI;
    }

    public short getSize() {
        return size;
    }

    public PacketType getType() {
        return type;
    }

    public short getReqI() {
        return reqI;
    }
}
