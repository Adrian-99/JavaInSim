/*
 * Copyright (c) 2025, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.internal.insim.packets.subtypes;

import com.github.adrian99.javainsim.internal.insim.packets.structures.base.ByteInstructionStructure;

import java.util.List;

/**
 * Base class for all packet subtypes.
 */
public abstract class PacketSubtype implements ByteInstructionStructure {
    /**
     * ordinal value of subtype
     */
    protected final short value;

    /**
     * Creates packet subtype value
     * @param value ordinal value of subtype
     */
    protected PacketSubtype(int value) {
        this.value = (short) value;
    }

    /**
     * @return ordinal value of the subtype
     */
    @Override
    public short getByteValue() {
        return value;
    }

    /**
     * Converts ordinal number to packet subtype.
     * @param allSubtypes list of all subtype values
     * @param ordinal ordinal number
     * @param defaultSubtype value to be returned when subtype with given ordinal was not found
     * @return enum value
     * @param <T> type of subtype
     */
    protected static <T extends PacketSubtype> T fromOrdinal(List<T> allSubtypes, int ordinal, T defaultSubtype) {
        for (var subtype : allSubtypes) {
            if (subtype.getByteValue() == ordinal) {
                return subtype;
            }
        }
        return defaultSubtype;
    }
}
