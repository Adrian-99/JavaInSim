/*
 * Copyright (c) 2025, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.subtypes.tiny;

import com.github.adrian99.javainsim.api.insim.packets.*;
import com.github.adrian99.javainsim.internal.insim.packets.subtypes.PacketSubtype;

/**
 * Subtype of {@link TinyPacket}.
 */
public class TinySubtype extends PacketSubtype {
    TinySubtype(int value) {
        super(value);
        TinySubtypes.ALL.add(this);
    }

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static TinySubtype fromOrdinal(int ordinal) {
        return fromOrdinal(TinySubtypes.ALL, ordinal, TinySubtypes.NONE);
    }
}
