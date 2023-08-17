/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.enums;

import com.github.adrian99.javainsim.api.insim.packets.CnlPacket;
import com.github.adrian99.javainsim.internal.common.enums.EnumHelpers;

/**
 * Enumeration for leave reason used in {@link CnlPacket CnlPacket}.
 */
public enum LeaveReason {
    /**
     * value 0: none
     */
    DISCO,
    /**
     * value 1: timed out
     */
    TIMEOUT,
    /**
     * value 2: lost connection
     */
    LOSTCONN,
    /**
     * value 3: kicked
     */
    KICKED,
    /**
     * value 4: banned
     */
    BANNED,
    /**
     * value 5: security
     */
    SECURITY,
    /**
     * value 6: cheat protection wrong
     */
    CPW,
    /**
     * value 7: out of sync with host
     */
    OOS,
    /**
     * value 8: join OOS (initial sync failed)
     */
    JOOS,
    /**
     * value 9: invalid packet
     */
    HACK;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static LeaveReason fromOrdinal(int ordinal) {
        return EnumHelpers.get(LeaveReason.class).fromOrdinal(ordinal);
    }
}
