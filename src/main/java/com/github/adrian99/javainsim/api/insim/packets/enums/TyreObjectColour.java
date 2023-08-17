/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.enums;

import com.github.adrian99.javainsim.api.insim.packets.structures.objectinfo.TyreObjectInfo;
import com.github.adrian99.javainsim.internal.common.enums.EnumHelpers;

/**
 * Enumeration for tyre object colours used in
 * {@link TyreObjectInfo TyreObjectInfo}.
 */
public enum TyreObjectColour {
    /**
     * value 0: black
     */
    BLACK,
    /**
     * value 1: white
     */
    WHITE,
    /**
     * value 2: red
     */
    RED,
    /**
     * value 3: blue
     */
    BLUE,
    /**
     * value 4: green
     */
    GREEN,
    /**
     * value 5: yellow
     */
    YELLOW;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static TyreObjectColour fromOrdinal(int ordinal) {
        return EnumHelpers.get(TyreObjectColour.class).fromOrdinal(ordinal);
    }
}
