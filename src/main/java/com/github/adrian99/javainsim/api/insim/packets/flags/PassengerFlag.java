/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.flags;

import com.github.adrian99.javainsim.internal.common.flags.FlagWithCustomValue;

/**
 * Enumeration for passenger flags.
 */
public enum PassengerFlag implements FlagWithCustomValue {
    /**
     * bit 0, value 1: front, male
     */
    FRONT_MALE(3),
    /**
     * bit 1, value 2: front, female
     */
    FRONT_FEMALE(3),
    /**
     * bit 2, value 4: rear left, male
     */
    REAR_LEFT_MALE(12),
    /**
     * bit 3, value 8: rear left, female
     */
    REAR_LEFT_FEMALE(12),
    /**
     * bit 4, value 16: rear middle, male
     */
    REAR_MIDDLE_MALE(48),
    /**
     * bit 5, value 32: rear middle, female
     */
    REAR_MIDDLE_FEMALE(48),
    /**
     * bit 6, value 64: rear right, male
     */
    REAR_RIGHT_MALE(192),
    /**
     * bit 7, value 128: rear right, female
     */
    REAR_RIGHT_FEMALE(192);

    private final short mask;

    PassengerFlag(int mask) {
        this.mask = (short) mask;
    }

    @Override
    public int getValue() {
        return 1 << ordinal();
    }

    @Override
    public int getValueMask() {
        return mask;
    }
}
