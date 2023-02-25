package pl.adrian.internal;

import pl.adrian.api.PacketListener;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.ReadablePacket;
import pl.adrian.internal.packets.base.RequestablePacket;

/**
 * This class stores information about packet request that has been made using
 * {@link pl.adrian.api.InSimConnection#request(Class, PacketListener) request} method of
 * {@link pl.adrian.api.InSimConnection InSimConnection}.
 * @param packetType type of the packet that has been requested
 * @param reqI reqI value that has been assigned to request tiny packet
 * @param callback function to be called when requested packet is received
 * @param <T> type of the packet that has been requested
 */
public record PacketRequest<T extends Packet & ReadablePacket & RequestablePacket>(
        PacketType packetType,
        short reqI,
        PacketListener<T> callback
) {}
