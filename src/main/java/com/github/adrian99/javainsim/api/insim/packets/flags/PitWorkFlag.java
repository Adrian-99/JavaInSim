/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.flags;

/**
 * Enumeration for pit work flags.
 */
public enum PitWorkFlag {
    /**
     * bit 0, value 1: nothing
     */
    NOTHING,
    /**
     * bit 1, value 2: stop
     */
    STOP,
    /**
     * bit 2, value 4: front dampers
     */
    FR_DAM,
    /**
     * bit 3, value 8: front wheels
     */
    FR_WHL,
    /**
     * bit 4, value 16: front left damper
     */
    LE_FR_DAM,
    /**
     * bit 5, value 32: front left wheel
     */
    LE_FR_WHL,
    /**
     * bit 6, value 64: front right damper
     */
    RI_FR_DAM,
    /**
     * bit 7, value 128: front right wheel
     */
    RI_FR_WHL,
    /**
     * bit 8, value 256: rear dampers
     */
    RE_DAM,
    /**
     * bit 9, value 512: rear wheels
     */
    RE_WHL,
    /**
     * bit 10, value 1024: rear left damper
     */
    LE_RE_DAM,
    /**
     * bit 11, value 2048: rear left wheel
     */
    LE_RE_WHL,
    /**
     * bit 12, value 4096: rear right damper
     */
    RI_RE_DAM,
    /**
     * bit 13, value 8192: rear right wheel
     */
    RI_RE_WHL,
    /**
     * bit 14, value 16384: body minor
     */
    BODY_MINOR,
    /**
     * bit 15, value 32768: body major
     */
    BODY_MAJOR,
    /**
     * bit 16, value 65536: setup
     */
    SETUP,
    /**
     * bit 17, value 131072: refuel
     */
    REFUEL
}
