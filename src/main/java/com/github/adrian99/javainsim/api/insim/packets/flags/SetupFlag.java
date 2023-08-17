/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.flags;

/**
 * Enumeration for setup flags.
 */
public enum SetupFlag {
    /**
     * bit 0, value 1: symmetric wheels
     */
    SYMM_WHEELS,
    /**
     * bit 1, value 2: traction control enabled
     */
    TC_ENABLE,
    /**
     * bit 2, value 4: ABS enabled
     */
    ABS_ENABLE
}
