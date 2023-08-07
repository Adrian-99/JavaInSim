/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.insim.packets.enums;

import pl.adrian.api.insim.packets.flags.StaFlag;
import pl.adrian.internal.common.enums.EnumWithCustomValue;

/**
 * Enumeration for flag field of {@link pl.adrian.api.insim.packets.SfpPacket SfpPacket}.
 */
public enum SfpFlag implements EnumWithCustomValue {
    /**
     * bit 6, value 64: SHIFT+U buttons hidden
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
