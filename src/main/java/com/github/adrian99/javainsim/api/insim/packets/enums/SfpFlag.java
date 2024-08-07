/*
 * Copyright (c) 2024, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.enums;

import com.github.adrian99.javainsim.api.insim.packets.SfpPacket;
import com.github.adrian99.javainsim.api.insim.packets.flags.StaFlag;
import com.github.adrian99.javainsim.internal.common.enums.EnumWithCustomValue;

/**
 * Enumeration for flag field of {@link SfpPacket SfpPacket}.
 */
public enum SfpFlag implements EnumWithCustomValue {
    /**
     * bit 6, value 64: free view buttons hidden
     */
    SHIFTU_NO_OPT(StaFlag.SHIFTU_NO_OPT.ordinal()),
    /**
     * bit 7, value 128: showing 2d display
     */
    SHOW_2D(StaFlag.SHOW_2D.ordinal()),
    /**
     * bit 10, value 1024: multiplayer speedup option
     */
    MPSPEEDUP(StaFlag.MPSPEEDUP.ordinal()),
    /**
     * bit 12, value 4096: sound is switched off
     */
    SOUND_MUTE(StaFlag.SOUND_MUTE.ordinal());

    private final int value;

    SfpFlag(int staFlagBit) {
        value = 1 << staFlagBit;
    }

    @Override
    public int getValue() {
        return value;
    }
}
