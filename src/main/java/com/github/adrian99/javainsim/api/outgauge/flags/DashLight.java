/*
 * Copyright (c) 2024, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.outgauge.flags;

import com.github.adrian99.javainsim.api.insim.packets.AiiPacket;
import com.github.adrian99.javainsim.api.outgauge.OutGaugePacket;
import com.github.adrian99.javainsim.internal.common.flags.FlagWithCustomValue;

/**
 * Flags for dash lights used in {@link OutGaugePacket} and {@link AiiPacket}.
 */
public enum DashLight implements FlagWithCustomValue {
    /**
     * bit 0, value 1: shift light
     */
    SHIFT(0),
    /**
     * bit 1, value 2: full beam
     */
    FULLBEAM(1),
    /**
     * bit 2, value 4: handbrake
     */
    HANDBRAKE(2),
    /**
     * bit 3, value 8: pit speed limiter
     */
    PITSPEED(3),
    /**
     * bit 4, value 16: TC active or switched off
     */
    TC(4),
    /**
     * bit 5, value 32: left turn signal
     */
    SIGNAL_L(5),
    /**
     * bit 6, value 64: right turn signal
     */
    SIGNAL_R(6),
    /**
     * bit 7, value 128: shared turn signal
     */
    SIGNAL_ANY(7),
    /**
     * bit 8, value 256: oil pressure warning
     */
    OILWARN(8),
    /**
     * bit 9, value 512: battery warning
     */
    BATTERY(9),
    /**
     * bit 10, value 1024: ABS active or switched off
     */
    ABS(10),
    /**
     * bit 11, value 2048: engine damage
     */
    ENGINE(11),
    /**
     * bit 12, value 4096: rear fog lights
     */
    FOG_REAR(12),
    /**
     * bit 13, value 8192: front fog lights
     */
    FOG_FRONT(13),
    /**
     * bit 14, value 16384: dipped headlight symbol
     */
    DIPPED(14),
    /**
     * bit 15, value 32768: low fuel warning light
     */
    FUELWARN(15),
    /**
     * bit 16, value 65536: sidelights symbol
     */
    SIDELIGHTS(16),
    /**
     * bit 17, value 131072: neutral light
     */
    NEUTRAL(17),
    /**
     * bit 28, value 268435456: severe engine damage
     */
    ENGINE_SEVERE(28);

    private final int value;

    DashLight(int bit) {
        this.value = 1 << bit;
    }

    @Override
    public int getValueMask() {
        return value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
