/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.enums;

import com.github.adrian99.javainsim.api.insim.packets.UcoPacket;
import com.github.adrian99.javainsim.internal.common.enums.EnumHelpers;

/**
 * Enumeration for actions used in {@link UcoPacket UcoPacket}.
 */
public enum UcoAction {
    /**
     * value 0: entered a circle
     */
    CIRCLE_ENTER,
    /**
     * value 1: left a circle
     */
    CIRCLE_LEAVE,
    /**
     * value 2: crossed cp in forward direction
     */
    CP_FWD,
    /**
     * value 3: crossed cp in reverse direction
     */
    CP_REV;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static UcoAction fromOrdinal(int ordinal) {
        return EnumHelpers.get(UcoAction.class).fromOrdinal(ordinal);
    }
}
