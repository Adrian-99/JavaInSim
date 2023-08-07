/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.insim.packets.flags;

import pl.adrian.api.insim.packets.CppPacket;
import pl.adrian.internal.common.flags.FlagWithCustomValue;

/**
 * Flags used in {@link CppPacket}.
 */
public enum CppFlag implements FlagWithCustomValue {
    /**
     * bit 3, value 8: in SHIFT+U mode
     */
    SHIFTU(8),
    /**
     * bit 5, value 32: FOLLOW view
     */
    SHIFTU_FOLLOW(32),
    /**
     * bit 13, value 8192: override user view
     */
    VIEW_OVERRIDE(8192);

    private final short value;

    CppFlag(int value) {
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
