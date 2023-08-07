/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.insim.packets.flags;

import pl.adrian.internal.common.flags.FlagWithCustomValue;

/**
 * Enumeration for flags used in {@link pl.adrian.api.insim.packets.NcnPacket NcnPacket}.
 */
public enum NcnFlag implements FlagWithCustomValue {
    /**
     * bit 2, value 4: remote
     */
    REMOTE(4);

    private final short value;

    NcnFlag(int value) {
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
