package pl.adrian.internal.packets.base;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.util.PacketReader;

public interface ReadablePacket {
    short getSize();

    PacketType getType();

    short getReqI();

    ReadablePacket readDataBytes(PacketReader packetReader);
}
