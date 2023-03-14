package pl.adrian.internal.packets.util;

import pl.adrian.api.PacketListener;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.enums.TinySubtype;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.InfoPacket;

import java.time.LocalDateTime;

/**
 * This class stores information about packet request that has been made using
 * {@link pl.adrian.api.InSimConnection#request(Class, PacketListener) request(Class, PacketListener)} or
 * {@link pl.adrian.api.InSimConnection#request(TinySubtype, PacketListener) request(TinySubtype, PacketListener)} method of
 * {@link pl.adrian.api.InSimConnection InSimConnection}.
 * @param <T> type of the packet that has been requested
 */
public class PacketRequest<T extends Packet & InfoPacket> {
    private final PacketType packetType;
    private final boolean expectMultiPacketResponse;
    private final short reqI;
    private final PacketListener<T> callback;

    private LocalDateTime lastUpdateAt;

    /**
     * Creates packet request info.
     * @param packetType type of the packet that has been requested
     * @param expectMultiPacketResponse whether multiple packets are expected in response
     * @param reqI reqI value that has been assigned to request tiny packet
     * @param callback function to be called when requested packet is received
     */
    public PacketRequest(PacketType packetType,
                         boolean expectMultiPacketResponse,
                         short reqI,
                         PacketListener<T> callback) {
        this.packetType = packetType;
        this.expectMultiPacketResponse = expectMultiPacketResponse;
        this.reqI = reqI;
        this.callback = callback;
        lastUpdateAt = LocalDateTime.now();
    }

    /**
     * @return type of the packet that has been requested
     */
    public PacketType getPacketType() {
        return packetType;
    }

    /**
     * @return whether multiple packets are expected in response
     */
    public boolean isExpectMultiPacketResponse() {
        return expectMultiPacketResponse;
    }

    /**
     * @return reqI value that has been assigned to request tiny packet
     */
    public short getReqI() {
        return reqI;
    }

    /**
     * @return function to be called when requested packet is received
     */
    public PacketListener<T> getCallback() {
        return callback;
    }

    /**
     * @return time of the last packet request update (creation or requested packet received)
     */
    public LocalDateTime getLastUpdateAt() {
        return lastUpdateAt;
    }

    /**
     * Sets last update time of the request to current time.
     */
    public void setLastUpdateNow() {
        lastUpdateAt = LocalDateTime.now();
    }
}
