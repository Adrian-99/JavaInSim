/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.enums;

import com.github.adrian99.javainsim.internal.common.enums.EnumWithCustomValue;
import com.github.adrian99.javainsim.internal.common.enums.EnumHelpers;

/**
 * Enumeration for flag types.
 */
public enum FlagType implements EnumWithCustomValue {
    /**
     * value 1: given blue flag
     */
    GIVEN_BLUE(1),
    /**
     * value 2: causing yellow flag
     */
    CAUSING_YELLOW(2);

    private final short value;

    FlagType(int value) {
        this.value = (short) value;
    }

    @Override
    public int getValue() {
        return value;
    }

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static FlagType fromOrdinal(int ordinal) {
        return EnumHelpers.get(FlagType.class).fromOrdinal(ordinal);
    }
}
