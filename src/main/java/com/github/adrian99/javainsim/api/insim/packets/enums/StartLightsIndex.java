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
 * Enumeration for start lights index used in {@link OcoPacket}.
 */
public enum StartLightsIndex implements EnumWithCustomValue {
    /**
     * value 149: overrides temporary start lights in the layout
     */
    START_LIGHTS(149),
    /**
     * value 240: special value to override the main start light system
     */
    INDEX_MAIN(240);

    private final byte value;

    StartLightsIndex(int value) {
        this.value = (byte) value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
