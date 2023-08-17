/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.flags;

/**
 * Enumeration for player flags.
 */
public enum PlayerFlag {
    /**
     * bit 0, value 1: left side
     */
    LEFTSIDE,
    /**
     * bit 1, value 2: spare
     */
    RESERVED_2,
    /**
     * bit 2, value 4: spare
     */
    RESERVED_4,
    /**
     * bit 3, value 8: auto gear shift
     */
    AUTOGEARS,
    /**
     * bit 4, value 16: shifter
     */
    SHIFTER,
    /**
     * bit 5, value 32: spare
     */
    RESERVED_32,
    /**
     * bit 6, value 64: brake help
     */
    HELP_B,
    /**
     * bit 7, value 128: axis clutch
     */
    AXIS_CLUTCH,
    /**
     * bit 8, value 256: in pits
     */
    INPITS,
    /**
     * bit 9, value 512: auto clutch
     */
    AUTOCLUTCH,
    /**
     * bit 10, value 1024: mouse
     */
    MOUSE,
    /**
     * bit 11, value 2048: keyboard no help
     */
    KB_NO_HELP,
    /**
     * bit 12, value 4096: keyboard stabilised
     */
    KB_STABILISED,
    /**
     * bit 13, value 8192: custom view
     */
    CUSTOM_VIEW
}
