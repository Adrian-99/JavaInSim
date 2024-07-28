/*
 * Copyright (c) 2023, Adrian-99
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
 * Flags used in {@link SmallSubtype#LCS} {@link SmallPacket SmallPacket}.
 */
public enum LcsFlag implements FlagWithCustomValue {
    /**
     * signals off
     * @deprecated {@link SmallSubtype#LCL} {@link SmallPacket SmallPacket} should be used instead
     */
    @Deprecated
    SIGNALS_OFF(1, 769),
    /**
     * signals left
     * @deprecated {@link SmallSubtype#LCL} {@link SmallPacket SmallPacket} should be used instead
     */
    @Deprecated
    SIGNALS_LEFT(257, 769),
    /**
     * signals right
     * @deprecated {@link SmallSubtype#LCL} {@link SmallPacket SmallPacket} should be used instead
     */
    @Deprecated
    SIGNALS_RIGHT(513, 769),
    /**
     * signals hazard
     * @deprecated {@link SmallSubtype#LCL} {@link SmallPacket SmallPacket} should be used instead
     */
    @Deprecated
    SIGNALS_HAZARD(769, 769),
    /**
     * flash off
     */
    FLASH_OFF(2, 1026),
    /**
     * flash on
     */
    FLASH_ON(1026, 1026),
    /**
     * headlights off
     * @deprecated {@link SmallSubtype#LCL} {@link SmallPacket SmallPacket} should be used instead
     */
    @Deprecated
    HEADLIGHTS_OFF(4, 2052),
    /**
     * headlights on
     * @deprecated {@link SmallSubtype#LCL} {@link SmallPacket SmallPacket} should be used instead
     */
    @Deprecated
    HEADLIGHTS_ON(2052, 2052),
    /**
     * horn off
     */
    HORN_OFF(8, 458760),
    /**
     * horn 1
     */
    HORN_1(65544, 458760),
    /**
     * horn 2
     */
    HORN_2(131080, 458760),
    /**
     * horn 3
     */
    HORN_3(196616, 458760),
    /**
     * horn 4
     */
    HORN_4(262152, 458760),
    /**
     * horn 5
     */
    HORN_5(327688, 458760),
    /**
     * siren off
     */
    SIREN_OFF(16, 3145744),
    /**
     * siren fast
     */
    SIREN_FAST(1048592, 3145744),
    /**
     * siren slow
     */
    SIREN_SLOW(2097168, 3145744);

    private final int value;
    private final int valueMask;

    LcsFlag(int value, int valueMask) {
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
