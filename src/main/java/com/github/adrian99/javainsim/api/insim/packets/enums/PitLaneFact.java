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
 * Enumeration for pit lane facts.
 */
public enum PitLaneFact {
    /**
     * value 0: left pit lane
     */
    EXIT,
    /**
     * value 1: entered pit lane
     */
    ENTER,
    /**
     * value 2: entered for no purpose
     */
    NO_PURPOSE,
    /**
     * value 3: entered for drive-through
     */
    DT,
    /**
     * value 4: entered for stop-go
     */
    SG;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static PitLaneFact fromOrdinal(int ordinal) {
        return EnumHelpers.get(PitLaneFact.class).fromOrdinal(ordinal);
    }
}
