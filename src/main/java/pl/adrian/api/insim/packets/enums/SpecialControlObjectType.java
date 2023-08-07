/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.insim.packets.enums;

import pl.adrian.api.insim.packets.structures.objectinfo.SpecialControlObjectInfo;
import pl.adrian.internal.common.enums.EnumHelpers;

/**
 * Enumeration for special control object type used in {@link SpecialControlObjectInfo}.
 */
public enum SpecialControlObjectType {
    /**
     * value 0, width > 0: finish line
     */
    FINISH_LINE,
    /**
     * value 1: checkpoint 1
     */
    CHECKPOINT_1,
    /**
     * value 2: checkpoint 2
     */
    CHECKPOINT_2,
    /**
     * value 3: checkpoint 3
     */
    CHECKPOINT_3,
    /**
     * value 0, width 0: start position
     */
    START_POSITION;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static SpecialControlObjectType fromOrdinal(int ordinal) {
        return EnumHelpers.get(SpecialControlObjectType.class).fromOrdinal(ordinal);
    }
}
