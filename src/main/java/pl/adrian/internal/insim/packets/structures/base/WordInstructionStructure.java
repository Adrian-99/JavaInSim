package pl.adrian.internal.insim.packets.structures.base;

import pl.adrian.internal.insim.packets.base.InstructionPacket;

/**
 * This interface must be implemented by all classes that represent single field structures, which are
 * supposed to be sent to LFS within {@link InstructionPacket InstructionPackets} in form of word.
 */
public interface WordInstructionStructure {
    /**
     * @return word value of structure
     */
    int getWordValue();
}
