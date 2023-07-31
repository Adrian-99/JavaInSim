package pl.adrian.internal.insim.packets.base;

/**
 * This interface must be implemented by all packet classes that represent packets
 * which can be sent to LFS.
 */
public interface InstructionPacket extends Packet {
    /**
     * Converts packet to array of bytes representation.
     * @return Packet in form of array of bytes
     */
    byte[] getBytes();
}
