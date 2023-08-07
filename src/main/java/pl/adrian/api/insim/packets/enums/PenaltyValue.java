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
 * Enumeration for penalty values.
 */
public enum PenaltyValue {
    /**
     * value 0: none
     */
    NONE,
    /**
     * value 1: drive-through
     */
    DT,
    /**
     * value 2: drive-through (can now be cleared)
     */
    DT_VALID,
    /**
     * value 3: stop-go
     */
    SG,
    /**
     * value 4: stop-go (can now be cleared)
     */
    SG_VALID,
    /**
     * value 5: 30 second time
     */
    PLUS_30_S,
    /**
     * value 6: 45 second time
     */
    PLUS_45_S;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static PenaltyValue fromOrdinal(int ordinal) {
        return EnumHelpers.get(PenaltyValue.class).fromOrdinal(ordinal);
    }
}
