package pl.adrian.internal.packets.util;

import pl.adrian.api.PacketListener;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.enums.TinySubtype;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.InfoPacket;

/**
 * This class stores information about packet request that has been made using
 * {@link pl.adrian.api.InSimConnection#request(Class, PacketListener) request(Class, PacketListener)} or
 * {@link pl.adrian.api.InSimConnection#request(TinySubtype, PacketListener) request(TinySubtype, PacketListener)} method of
 * {@link pl.adrian.api.InSimConnection InSimConnection}.
 * @param packetType type of the packet that has been requested
 * @param reqI reqI value that has been assigned to request tiny packet
 * @param callback function to be called when requested packet is received
 * @param <T> type of the packet that has been requested
 */
public record PacketRequest<T extends Packet & InfoPacket>(
        PacketType packetType,
        short reqI,
        PacketListener<T> callback
) {}
