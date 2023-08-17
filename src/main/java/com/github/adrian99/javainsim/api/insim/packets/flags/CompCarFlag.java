/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.flags;

import com.github.adrian99.javainsim.api.insim.packets.structures.CompCar;
import com.github.adrian99.javainsim.internal.common.flags.FlagWithCustomValue;

/**
 * Enumeration for flags used in {@link CompCar CompCar}.
 */
public enum CompCarFlag implements FlagWithCustomValue {
    /**
     * bit 0, value 1: this car is in the way of a driver who is a lap ahead
     */
    BLUE(1),
    /**
     * bit 1, value 2: this car is slow or stopped and in a dangerous place
     */
    YELLOW(2),
    /**
     * bit 5, value 32: this car is lagging (missing or delayed position packets)
     */
    LAG(32),
    /**
     * bit 6, value 64: this is the first compcar in this set of MCI packets
     */
    FIRST(64),
    /**
     * bit 7, value 128: this is the last compcar in this set of MCI packets
     */
    LAST(128);

    private final short value;

    CompCarFlag(int value) {
        this.value = (short) value;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public int getValueMask() {
        return value;
    }
}
