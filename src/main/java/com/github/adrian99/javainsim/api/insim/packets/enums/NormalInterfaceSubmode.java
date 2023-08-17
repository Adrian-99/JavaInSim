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
 * Enumeration for normal interface submodes used in {@link CimPacket CimPacket}.
 */
public enum NormalInterfaceSubmode {
    /**
     * value 0: normal
     */
    NORMAL,
    /**
     * value 1: wheel temperatures (F9)
     */
    WHEEL_TEMPS,
    /**
     * value 2: wheel damage (F10)
     */
    WHEEL_DAMAGE,
    /**
     * value 3: live settings (F11)
     */
    LIVE_SETTINGS,
    /**
     * value 4: instructions (F12)
     */
    PIT_INSTRUCTIONS;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static NormalInterfaceSubmode fromOrdinal(int ordinal) {
        return EnumHelpers.get(NormalInterfaceSubmode.class).fromOrdinal(ordinal);
    }
}
