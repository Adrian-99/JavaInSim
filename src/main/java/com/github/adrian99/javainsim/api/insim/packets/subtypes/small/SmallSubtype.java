/*
 * Copyright (c) 2025, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.subtypes.small;

import com.github.adrian99.javainsim.api.insim.packets.SmallPacket;
import com.github.adrian99.javainsim.internal.insim.packets.subtypes.PacketSubtype;

/**
 * Subtype of {@link SmallPacket}
 */
public class SmallSubtype extends PacketSubtype {
    SmallSubtype(int value) {
        super(value);
        SmallSubtypes.ALL.add(this);
    }

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static SmallSubtype fromOrdinal(int ordinal) {
        return fromOrdinal(SmallSubtypes.ALL, ordinal, SmallSubtypes.NONE);
    }
}
