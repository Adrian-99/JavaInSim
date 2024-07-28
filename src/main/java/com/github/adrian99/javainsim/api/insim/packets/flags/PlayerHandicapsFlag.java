/*
 * Copyright (c) 2024, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.flags;

import com.github.adrian99.javainsim.api.insim.packets.structures.PlayerHandicaps;
import com.github.adrian99.javainsim.internal.common.flags.FlagWithCustomValue;

/**
 * Enumeration for flags used in {@link PlayerHandicaps}.
 */
public enum PlayerHandicapsFlag implements FlagWithCustomValue {
    /**
     * set mass handicap
     */
    SET_MASS((short) 1),
    /**
     * set intake restriction handicap
     */
    SET_TRES((short) 2),
    /**
     * avoid showing a message on player's screen
     */
    SILENT((short) 128);

    private final short value;

    PlayerHandicapsFlag(short value) {
        this.value = value;
    }

    @Override
    public int getValueMask() {
        return value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
