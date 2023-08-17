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
 * Enumeration for admin command result.
 */
public enum AcrResult {
    /**
     * 0 - none (unused)
     */
    NONE,
    /**
     * 1 - processed
     */
    PROCESSED,
    /**
     * 2 - rejected
     */
    REJECTED,
    /**
     * 3 - unknown command
     */
    UNKNOWN_COMMAND;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static AcrResult fromOrdinal(int ordinal) {
        return EnumHelpers.get(AcrResult.class).fromOrdinal(ordinal);
    }
}
