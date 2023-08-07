/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.internal.insim.packets.structures.base;

import pl.adrian.internal.insim.packets.base.InstructionPacket;
import pl.adrian.internal.insim.packets.util.PacketBuilder;

/**
 * This interface must be implemented by all classes that represent multi-field structures, which are
 * supposed to be sent to LFS within {@link InstructionPacket InstructionPackets}.
 */
public interface ComplexInstructionStructure {
    /**
     * Appends byte representation of structure to specified {@link PacketBuilder}.
     * @param packetBuilder {@link PacketBuilder} to append bytes to
     */
    void appendBytes(PacketBuilder packetBuilder);
}
