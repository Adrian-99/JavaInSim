/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.enums;

import com.github.adrian99.javainsim.api.insim.packets.structures.objectinfo.ChalkLineInfo;
import com.github.adrian99.javainsim.internal.common.enums.EnumHelpers;

/**
 * Enumeration for colours of chalk line used in
 * {@link ChalkLineInfo ChalkLineInfo}.
 */
public enum ChalkLineColour {
    /**
     * value 0: white
     */
    WHITE,
    /**
     * value 1: red
     */
    RED,
    /**
     * value 2: blue
     */
    BLUE,
    /**
     * value 3: yellow
     */
    YELLOW;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static ChalkLineColour fromOrdinal(int ordinal) {
        return EnumHelpers.get(ChalkLineColour.class).fromOrdinal(ordinal);
    }
}
