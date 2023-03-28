package pl.adrian.internal.packets.structures.base;

import pl.adrian.internal.packets.base.InstructionPacket;

/**
 * This interface must be implemented by all classes that represent single field structures, which are
 * supposed to be sent to LFS within {@link InstructionPacket InstructionPackets} in form of unsigned.
 */
public interface UnsignedInstructionStructure {
    /**
     * @return unsigned value of structure
     */
    long getUnsignedValue();
}