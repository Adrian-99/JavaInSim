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
 * Enumeration for InSim checkpoint types.
 */
public enum InSimCheckpointType {
    /**
     * value 0: finish line
     */
    FINISH_LINE,
    /**
     * value 1: 1st checkpoint
     */
    CHECKPOINT_1,
    /**
     * value 2: 2nd checkpoint
     */
    CHECKPOINT_2,
    /**
     * value 3: 3rd checkpoint
     */
    CHECKPOINT_3;

    /**
     * Converts ordinal number to enum value
     * @param ordinal ordinal number
     * @return enum value
     */
    public static InSimCheckpointType fromOrdinal(int ordinal) {
        return EnumHelpers.get(InSimCheckpointType.class).fromOrdinal(ordinal);
    }
}
