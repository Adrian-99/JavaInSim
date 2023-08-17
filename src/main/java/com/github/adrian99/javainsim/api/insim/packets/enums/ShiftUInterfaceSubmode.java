/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.enums;

import com.github.adrian99.javainsim.api.insim.packets.CimPacket;
import com.github.adrian99.javainsim.internal.common.enums.EnumHelpers;

/**
 * Enumeration for free view interface submodes used in {@link CimPacket CimPacket}.
 */
public enum ShiftUInterfaceSubmode {
    /**
     * value 0: no buttons displayed
     */
    PLAIN,
    /**
     * value 1: buttons displayed (not editing)
     */
    BUTTONS,
    /**
     * value 2: edit mode
     */
    EDIT;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static ShiftUInterfaceSubmode fromOrdinal(int ordinal) {
        return EnumHelpers.get(ShiftUInterfaceSubmode.class).fromOrdinal(ordinal);
    }
}
