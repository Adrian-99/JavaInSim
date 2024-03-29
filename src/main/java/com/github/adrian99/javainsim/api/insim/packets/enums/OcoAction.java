/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.enums;

import com.github.adrian99.javainsim.api.insim.packets.OcoPacket;
import com.github.adrian99.javainsim.internal.common.enums.EnumWithCustomValue;

/**
 * Enumeration for actions used in {@link OcoPacket}.
 */
public enum OcoAction implements EnumWithCustomValue {
    /**
     * value 4: give up control of all lights
     */
    LIGHTS_RESET(4),
    /**
     * value 5: use Data byte to set the bulbs
     */
    LIGHTS_SET(5),
    /**
     * value 6: give up control of the specified lights
     */
    LIGHTS_UNSET(6);

    private final byte value;

    OcoAction(int value) {
        this.value = (byte) value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
