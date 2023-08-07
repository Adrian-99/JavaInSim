/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.outgauge.flags;

import pl.adrian.api.outgauge.OutGaugePacket;
import pl.adrian.internal.common.flags.FlagWithCustomValue;

/**
 * Flags used in {@link OutGaugePacket}.
 */
public enum OutGaugeFlag implements FlagWithCustomValue {
    /**
     * bit 0, value 1: shift key
     */
    SHIFT(1),
    /**
     * bit 1, value 2: ctrl key
     */
    CTRL(2),
    /**
     * bit 13, value 8192: show turbo gauge
     */
    TURBO(8192),
    /**
     * bit 14, value 16384: if not set - user prefers MILES
     */
    KM(16384),
    /**
     * bit 15, value 32768: if not set - user prefers PSI
     */
    BAR(32768);

    private final int value;

    OutGaugeFlag(int value) {
        this.value = value;
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
