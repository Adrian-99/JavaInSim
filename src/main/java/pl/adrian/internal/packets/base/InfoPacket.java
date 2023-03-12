package pl.adrian.internal.packets.base;

import pl.adrian.api.packets.enums.PacketType;

/**
 * This interface must be implemented by all packet classes that represent packets
 * which can be received from LFS.
 */
public interface InfoPacket {
    /**
     * @return total packet size - a multiple of 4
     */
    short getSize();

    /**
     * @return packet identifier
     */
    PacketType getType();

    /**
     * @return non-zero if the packet is a reply to a request
     */
    short getReqI();
}
