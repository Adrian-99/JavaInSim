/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.enums;

import com.github.adrian99.javainsim.api.insim.packets.structures.objectinfo.ConcreteObjectInfo;
import com.github.adrian99.javainsim.internal.common.enums.EnumHelpers;

/**
 * Enumeration for colours of concrete objects used in
 * {@link ConcreteObjectInfo ConcreteObjectInfo}
 * and its inheritors.
 */
public enum ConcreteObjectColour {
    /**
     * value 0: grey
     */
    GREY,
    /**
     * value 1: red
     */
    RED,
    /**
     * value 2: blue
     */
    BLUE,
    /**
     * Value 3: yellow
     */
    YELLOW;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static ConcreteObjectColour fromOrdinal(int ordinal) {
        return EnumHelpers.get(ConcreteObjectColour.class).fromOrdinal(ordinal);
    }
}
