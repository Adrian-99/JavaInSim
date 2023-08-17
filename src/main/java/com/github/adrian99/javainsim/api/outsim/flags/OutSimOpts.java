/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.outsim.flags;

/**
 * Enumeration for OutSim options.
 */
public enum OutSimOpts {
    /**
     * bit 0, value 1: header
     */
    HEADER,
    /**
     * bit 1, value 2: id
     */
    ID,
    /**
     * bit 2, value 4: time
     */
    TIME,
    /**
     * bit 3, value 8: main
     */
    MAIN,
    /**
     * bit 4, value 16: inputs
     */
    INPUTS,
    /**
     * bit 5, value 32: drive
     */
    DRIVE,
    /**
     * bit 6, value 64: distance
     */
    DISTANCE,
    /**
     * bit 7, value 128: wheels
     */
    WHEELS,
    /**
     * bit 8, value 256: extra 1
     */
    EXTRA_1
}
