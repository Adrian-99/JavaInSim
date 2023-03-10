package pl.adrian.internal.packets.structures;

import pl.adrian.internal.packets.base.InstructionPacket;
import pl.adrian.internal.packets.util.PacketBuilder;

/**
 * This interface must be implemented by all classes that represent structures, which are
 * supposed to be sent to LFS within {@link InstructionPacket InstructionPackets}.
 */
public interface InstructionStructure {
    /**
     * Appends byte representation of structure to specified {@link PacketBuilder}.
     * @param packetBuilder {@link PacketBuilder} to append bytes to
     */
    void appendBytes(PacketBuilder packetBuilder);
}
