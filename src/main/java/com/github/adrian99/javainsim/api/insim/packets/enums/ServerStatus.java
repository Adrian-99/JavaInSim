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
 * Enumeration for server status.
 */
public enum ServerStatus {
    /**
     * 0 - unknown
     */
    UNKNOWN,
    /**
     * 1 - success
     */
    SUCCESS,
    /**
     * 2 - fail
     */
    FAIL;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static ServerStatus fromOrdinal(int ordinal) {
        return EnumHelpers.get(ServerStatus.class).fromOrdinal(ordinal);
    }
}
