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
 * Enumeration for view identifiers.
 */
public enum ViewIdentifier implements EnumWithCustomValue {
    /**
     * value 0 - arcade
     */
    FOLLOW,
    /**
     * value 1 - helicopter
     */
    HELI,
    /**
     * value 2 - tv camera
     */
    CAM,
    /**
     * value 3 - cockpit
     */
    DRIVER,
    /**
     * value 4 - custom
     */
    CUSTOM,
    /**
     * value 5 - ???
     */
    MAX,
    /**
     * value 255 - viewing another car
     */
    ANOTHER;

    @Override
    public int getValue() {
        return !this.equals(ANOTHER) ? ordinal() : 255;
    }

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static ViewIdentifier fromOrdinal(int ordinal) {
        return ordinal != 255 ? EnumHelpers.get(ViewIdentifier.class).fromOrdinal(ordinal) : ANOTHER;
    }
}
