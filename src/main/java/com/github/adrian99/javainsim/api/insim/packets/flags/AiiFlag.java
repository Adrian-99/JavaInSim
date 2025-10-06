/*
 * Copyright (c) 2025, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.flags;

import com.github.adrian99.javainsim.api.insim.packets.AiiPacket;
import com.github.adrian99.javainsim.internal.common.flags.FlagWithCustomValue;

/**
 * Enumeration for flags used in {@link AiiPacket}.
 */
public enum AiiFlag implements FlagWithCustomValue {
    /**
     * bit 0, value 1: detect if engine running
     */
    IGNITION(1),
    /**
     * bit 2, value 4: upshift lever currently held
     */
    CHUP(4),
    /**
     * bit 3, value 8: downshift lever currently held
     */
    CHDN(8);

    private final short value;

    AiiFlag(int value) {
        this.value = (short) value;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public int getValueMask() {
        return value;
    }
}
