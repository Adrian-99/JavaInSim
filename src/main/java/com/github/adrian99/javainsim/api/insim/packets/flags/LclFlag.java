/*
 * Copyright (c) 2024, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.flags;

import com.github.adrian99.javainsim.api.insim.packets.SmallPacket;
import com.github.adrian99.javainsim.api.insim.packets.enums.SmallSubtype;
import com.github.adrian99.javainsim.internal.common.flags.FlagWithCustomValue;

/**
 * Flags used in {@link SmallSubtype#LCL} {@link SmallPacket SmallPacket}.
 */
public enum LclFlag implements FlagWithCustomValue {
    /**
     * signals off
     */
    SIGNALS_OFF(1, 196609),
    /**
     * signals left
     */
    SIGNALS_LEFT(65537, 196609),
    /**
     * signals right
     */
    SIGNALS_RIGHT(131073, 196609),
    /**
     * signals hazard
     */
    SIGNALS_HAZARD(196609, 196609),
    /**
     * lights off
     */
    LIGHTS_OFF(4, 786436),
    /**
     * lights side
     */
    LIGHTS_SIDE(262148, 786436),
    /**
     * lights low
     */
    LIGHTS_LOW(524292, 786436),
    /**
     * lights high
     */
    LIGHTS_HIGH(786436, 786436),
    /**
     * fog rear off
     */
    FOG_REAR_OFF(16, 1048592),
    /**
     * fog rear on
     */
    FOG_REAR_ON(1048592, 1048592),
    /**
     * fog front off
     */
    FOG_FRONT_OFF(32, 2097184),
    /**
     * fog front on
     */
    FOG_FRONT_ON(2097184, 2097184),
    /**
     * extra off
     */
    EXTRA_OFF(64, 4194368),
    /**
     * extra on
     */
    EXTRA_ON(4194368, 4194368);

    private final int value;
    private final int valueMask;

    LclFlag(int value, int valueMask) {
        this.value = value;
        this.valueMask = valueMask;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public int getValueMask() {
        return valueMask;
    }
}
