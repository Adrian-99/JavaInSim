/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.insim.packets.enums;

import pl.adrian.api.insim.packets.structures.objectinfo.RestrictedAreaInfo;
import pl.adrian.internal.common.enums.EnumHelpers;

/**
 * Enumeration for marshall type used in {@link RestrictedAreaInfo}.
 */
public enum MarshallType {
    /**
     * value 0: no marshall
     */
    NO_MARSHALL,
    /**
     * value 1: standing marshall
     */
    STANDING,
    /**
     * value 2: marshall pointing left
     */
    POINTING_LEFT,
    /**
     * value 3: marshall pointing right
     */
    POINTING_RIGHT;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static MarshallType fromOrdinal(int ordinal) {
        return EnumHelpers.get(MarshallType.class).fromOrdinal(ordinal);
    }
}
