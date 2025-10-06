/*
 * Copyright (c) 2025, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.subtypes.ttc;

import com.github.adrian99.javainsim.api.insim.packets.TtcPacket;
import com.github.adrian99.javainsim.internal.insim.packets.subtypes.PacketSubtype;

/**
 * Subtype of {@link TtcPacket}
 */
public class TtcSubtype extends PacketSubtype {
    TtcSubtype(int value) {
        super(value);
        TtcSubtypes.ALL.add(this);
    }

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static TtcSubtype fromOrdinal(int ordinal) {
        return fromOrdinal(TtcSubtypes.ALL, ordinal, TtcSubtypes.NONE);
    }
}
