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
 * Enumeration for wind.
 */
public enum Wind {
    /**
     * 0 - off
     */
    OFF,
    /**
     * 1 - weak
     */
    WEAK,
    /**
     * 2 - strong
     */
    STRONG;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static Wind fromOrdinal(int ordinal) {
        return EnumHelpers.get(Wind.class).fromOrdinal(ordinal);
    }
}
