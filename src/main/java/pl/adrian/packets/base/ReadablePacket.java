package pl.adrian.packets.base;

import pl.adrian.packets.enums.PacketType;
import pl.adrian.packets.util.PacketReader;

public interface ReadablePacket {
    short getSize();

    PacketType getType();

    short getReqI();

    ReadablePacket readDataBytes(PacketReader packetReader);
}
