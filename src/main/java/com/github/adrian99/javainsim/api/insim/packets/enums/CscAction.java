/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.enums;

import com.github.adrian99.javainsim.api.insim.packets.CscPacket;
import com.github.adrian99.javainsim.internal.common.enums.EnumHelpers;

/**
 * Enumeration for actions used in {@link CscPacket}.
 */
public enum CscAction {
    /**
     * value 0: stop
     */
    STOP,
    /**
     * value 1: start
     */
    START;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static CscAction fromOrdinal(int ordinal) {
        return EnumHelpers.get(CscAction.class).fromOrdinal(ordinal);
    }
}
