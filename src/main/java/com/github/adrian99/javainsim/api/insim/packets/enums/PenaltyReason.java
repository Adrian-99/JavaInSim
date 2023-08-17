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
 * Enumeration for penalty reasons.
 */
public enum PenaltyReason {
    /**
     * value 0: unknown or cleared penalty
     */
    UNKNOWN,
    /**
     * value 1: penalty given by admin
     */
    ADMIN,
    /**
     * value 2: wrong way driving
     */
    WRONG_WAY,
    /**
     * value 3: starting before green light
     */
    FALSE_START,
    /**
     * value 4: speeding in pit lane
     */
    SPEEDING,
    /**
     * value 5: stop-go pit stop too short
     */
    STOP_SHORT,
    /**
     * value 6: compulsory stop is too late
     */
    STOP_LATE;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static PenaltyReason fromOrdinal(int ordinal) {
        return EnumHelpers.get(PenaltyReason.class).fromOrdinal(ordinal);
    }
}
