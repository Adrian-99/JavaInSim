/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.insim.packets.enums;

import pl.adrian.internal.common.enums.EnumHelpers;

/**
 * Enumeration for vote actions.
 */
public enum VoteAction {
    /**
     * 0 - no vote
     */
    NONE,
    /**
     * 1 - end race
     */
    END,
    /**
     * 2 - restart
     */
    RESTART,
    /**
     * 3 - qualify
     */
    QUALIFY;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static VoteAction fromOrdinal(int ordinal) {
        return EnumHelpers.get(VoteAction.class).fromOrdinal(ordinal);
    }
}
