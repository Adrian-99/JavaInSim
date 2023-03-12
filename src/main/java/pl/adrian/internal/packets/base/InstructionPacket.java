package pl.adrian.internal.packets.base;

/**
 * This interface must be implemented by all packet classes that represent packets
 * which can be sent to LFS.
 */
public interface InstructionPacket {
    /**
     * Converts packet to array of bytes representation.
     * @return Packet in form of array of bytes
     */
    byte[] getBytes();
}
