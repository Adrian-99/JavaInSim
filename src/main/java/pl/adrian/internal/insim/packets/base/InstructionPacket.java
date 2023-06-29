package pl.adrian.internal.insim.packets.base;

import pl.adrian.api.insim.packets.enums.PacketType;

/**
 * This interface must be implemented by all packet classes that represent packets
 * which can be sent to LFS.
 */
public interface InstructionPacket {
    /**
     * @return packet identifier
     */
    PacketType getType();

    /**
     * Converts packet to array of bytes representation.
     * @return Packet in form of array of bytes
     */
    byte[] getBytes();
}
