/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.enums;

import com.github.adrian99.javainsim.api.insim.packets.structures.LapTiming;

/**
 * Enumeration for lap timing types used in {@link LapTiming LapTiming}.
 */
public enum LapTimingType {
    /**
     * standard lap timing
     */
    STANDARD,
    /**
     * custom timing - user checkpoints have been placed
     */
    CUSTOM,
    /**
     * no lap timing - e.g. open config with no user checkpoints
     */
    NONE
}
