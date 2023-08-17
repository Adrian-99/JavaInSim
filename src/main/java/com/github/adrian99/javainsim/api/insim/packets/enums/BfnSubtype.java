/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.enums;

import com.github.adrian99.javainsim.api.insim.packets.BfnPacket;
import com.github.adrian99.javainsim.internal.common.enums.EnumHelpers;

/**
 * Enumeration for subtypes of {@link BfnPacket}.
 */
public enum BfnSubtype {
    /**
     * value 0: instruction: delete one button or range of buttons (must set ClickID)
     */
    DEL_BTN,
    /**
     * value 1: instruction: clear all buttons made by this InSim instance
     */
    CLEAR,
    /**
     * value 2: info: user cleared this InSim instance's buttons
     */
    USER_CLEAR,
    /**
     * value 3: user request: SHIFT+B or SHIFT+I - request for buttons
     */
    REQUEST;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static BfnSubtype fromOrdinal(int ordinal) {
        return EnumHelpers.get(BfnSubtype.class).fromOrdinal(ordinal);
    }
}
