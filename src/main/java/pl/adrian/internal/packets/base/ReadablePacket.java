package pl.adrian.internal.packets.base;

import pl.adrian.api.packets.enums.PacketType;

public interface ReadablePacket {
    short getSize();

    PacketType getType();

    short getReqI();
}
