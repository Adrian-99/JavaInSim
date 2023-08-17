/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.outgauge.flags;

import com.github.adrian99.javainsim.api.outgauge.OutGaugePacket;

/**
 * Flags for dash lights used in {@link OutGaugePacket}.
 */
public enum DashLight {
    /**
     * bit 0, value 1: shift light
     */
    SHIFT,
    /**
     * bit 1, value 2: full beam
     */
    FULLBEAM,
    /**
     * bit 2, value 4: handbrake
     */
    HANDBRAKE,
    /**
     * bit 3, value 8: pit speed limiter
     */
    PITSPEED,
    /**
     * bit 4, value 16: TC active or switched off
     */
    TC,
    /**
     * bit 5, value 32: left turn signal
     */
    SIGNAL_L,
    /**
     * bit 6, value 64: right turn signal
     */
    SIGNAL_R,
    /**
     * bit 7, value 128: shared turn signal
     */
    SIGNAL_ANY,
    /**
     * bit 8, value 256: oil pressure warning
     */
    OILWARN,
    /**
     * bit 9, value 512: battery warning
     */
    BATTERY,
    /**
     * bit 10, value 1024: ABS active or switched off
     */
    ABS
}
