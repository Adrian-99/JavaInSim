/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.enums;

import com.github.adrian99.javainsim.api.insim.packets.SshPacket;
import com.github.adrian99.javainsim.internal.common.enums.EnumHelpers;

/**
 * Enumeration for error codes used in {@link SshPacket}.
 */
public enum SshErrorCode {
    /**
     * value 0: OK: completed instruction
     */
    OK,
    /**
     * value 1: can't save a screenshot - dedicated host
     */
    DEDICATED,
    /**
     * value 2: {@link SshPacket} corrupted (e.g. Name does not end with zero)
     */
    CORRUPTED,
    /**
     * value 3: could not save the screenshot
     */
    NO_SAVE;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static SshErrorCode fromOrdinal(int ordinal) {
        return EnumHelpers.get(SshErrorCode.class).fromOrdinal(ordinal);
    }
}
