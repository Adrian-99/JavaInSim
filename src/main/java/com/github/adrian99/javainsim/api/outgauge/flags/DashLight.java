/*
 * Copyright (c) 2024, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.outgauge.flags;

import com.github.adrian99.javainsim.api.outgauge.OutGaugePacket;
import com.github.adrian99.javainsim.internal.common.flags.FlagWithCustomValue;

/**
 * Flags for dash lights used in {@link OutGaugePacket}.
 */
public enum DashLight implements FlagWithCustomValue {
    /**
     * bit 0, value 1: shift light
     */
    SHIFT(1),
    /**
     * bit 1, value 2: full beam
     */
    FULLBEAM(2),
    /**
     * bit 2, value 4: handbrake
     */
    HANDBRAKE(4),
    /**
     * bit 3, value 8: pit speed limiter
     */
    PITSPEED(8),
    /**
     * bit 4, value 16: TC active or switched off
     */
    TC(16),
    /**
     * bit 5, value 32: left turn signal
     */
    SIGNAL_L(32),
    /**
     * bit 6, value 64: right turn signal
     */
    SIGNAL_R(64),
    /**
     * bit 7, value 128: shared turn signal
     */
    SIGNAL_ANY(128),
    /**
     * bit 8, value 256: oil pressure warning
     */
    OILWARN(256),
    /**
     * bit 9, value 512: battery warning
     */
    BATTERY(512),
    /**
     * bit 10, value 1024: ABS active or switched off
     */
    ABS(1024),
    /**
     * bit 11, value 2048: engine damage
     */
    ENGINE(2048),
    /**
     * bit 12, value 4096: rear fog lights
     */
    FOG_REAR(4096),
    /**
     * bit 13, value 8192: front fog lights
     */
    FOG_FRONT(8192),
    /**
     * bit 14, value 16384: dipped headlight symbol
     */
    DIPPED(16384),
    /**
     * bit 15, value 32786: low fuel warning light
     */
    FUELWARN(32786),
    /**
     * bit 16, value 65536: sidelights symbol
     */
    SIDELIGHTS(65536),
    /**
     * bit 17, value 131072: neutral light
     */
    NEUTRAL(131072),
    /**
     * bit 28, value 268435456: severe engine damage
     */
    ENGINE_SEVERE(268435456);

    private final int value;

    DashLight(int value) {
        this.value = value;
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
