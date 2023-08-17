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
 * Enumeration for interface modes used in {@link CimPacket CimPacket}.
 */
public enum InterfaceMode {
    /**
     * value 0: not in a special mode
     */
    NORMAL,
    /**
     * value 1: options
     */
    OPTIONS,
    /**
     * value 2: host options
     */
    HOST_OPTIONS,
    /**
     * value 3: garage
     */
    GARAGE,
    /**
     * value 4: car select
     */
    CAR_SELECT,
    /**
     * value 5: track select
     */
    TRACK_SELECT,
    /**
     * value 6: free view mode
     */
    SHIFTU;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static InterfaceMode fromOrdinal(int ordinal) {
        return EnumHelpers.get(InterfaceMode.class).fromOrdinal(ordinal);
    }
}
