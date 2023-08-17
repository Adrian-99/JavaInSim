/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.enums;

import com.github.adrian99.javainsim.internal.common.enums.EnumHelpers;

/**
 * Enumeration for race progress.
 */
public enum RaceProgress {
    /**
     * 0 - no race
     */
    NO_RACE,
    /**
     * 1 - race
     */
    RACE,
    /**
     * 2 - qualifying
     */
    QUALIFYING;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static RaceProgress fromOrdinal(int ordinal) {
        return EnumHelpers.get(RaceProgress.class).fromOrdinal(ordinal);
    }
}
