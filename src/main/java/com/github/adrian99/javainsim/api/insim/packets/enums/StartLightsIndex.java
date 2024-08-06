/*
 * Copyright (c) 2024, Adrian-99
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
     * value 149: layout start lights
     */
    START_LIGHTS1(149),
    /**
     * value 150: layout start lights
     */
    START_LIGHTS2(150),
    /**
     * value 151: layout start lights
     */
    START_LIGHTS3(151),
    /**
     * value 240: main start lights
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
