/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.internal.insim.packets.structures.base;

import com.github.adrian99.javainsim.internal.insim.packets.base.InstructionPacket;

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
