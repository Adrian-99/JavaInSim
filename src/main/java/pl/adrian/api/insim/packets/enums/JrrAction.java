/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.insim.packets.enums;

import pl.adrian.internal.common.enums.EnumWithCustomValue;

/**
 * Enumeration for action byte used in {@link pl.adrian.api.insim.packets.JrrPacket JrrPacket}.
 */
public enum JrrAction implements EnumWithCustomValue {
    /**
     * value 0: reject
     */
    REJECT(0),
    /**
     * value 1: allow
     */
    SPAWN(1),
    /**
     * value 4: reset
     */
    RESET(4),
    /**
     * value 5: reset without repair
     */
    RESET_NO_REPAIR(5);
    
    private final byte value;

    JrrAction(int value) {
        this.value = (byte) value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
