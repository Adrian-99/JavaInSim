/*
 * Copyright (c) 2025, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.subtypes.bfn;

import com.github.adrian99.javainsim.api.insim.packets.BfnPacket;
import com.github.adrian99.javainsim.internal.insim.packets.subtypes.PacketSubtype;

/**
 * Subtype of {@link BfnPacket}
 */
public class BfnSubtype extends PacketSubtype {
    BfnSubtype(int value) {
        super(value);
        BfnSubtypes.ALL.add(this);
    }

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static BfnSubtype fromOrdinal(int ordinal) {
        return fromOrdinal(BfnSubtypes.ALL, ordinal, null);
    }
}
