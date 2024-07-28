/*
 * Copyright (c) 2024, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.enums;

import com.github.adrian99.javainsim.api.insim.packets.NciPacket;
import com.github.adrian99.javainsim.internal.common.enums.EnumHelpers;

/**
 * Enumeration for LFS licenses used in {@link NciPacket}
 */
public enum License {
    /**
     * value 0: demo
     */
    DEMO,
    /**
     * value 1: S1
     */
    S1,
    /**
     * value 2: S2
     */
    S2,
    /**
     * value 3: S3
     */
    S3;

    /**
     * Converts ordinal number to enum value
     * @param ordinal ordinal number
     * @return enum value
     */
    public static License fromOrdinal(int ordinal) {
        return EnumHelpers.get(License.class).fromOrdinal(ordinal);
    }
}
