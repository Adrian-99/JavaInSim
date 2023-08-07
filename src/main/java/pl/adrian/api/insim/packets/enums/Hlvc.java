/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.insim.packets.enums;

import pl.adrian.internal.common.enums.EnumHelpers;
import pl.adrian.internal.common.enums.EnumWithCustomValue;

/**
 * Enumeration for HLVC used in {@link pl.adrian.api.insim.packets.HlvPacket HlvPacket}.
 */
public enum Hlvc implements EnumWithCustomValue {
    /**
     * value 0: ground
     */
    GROUND(0),
    /**
     * value 1: wall
     */
    WALL(1),
    /**
     * value 4: speeding
     */
    SPEEDING(4),
    /**
     * value 5: out of bounds
     */
    OUT_OF_BOUNDS(5);

    private final byte value;

    Hlvc(int value) {
        this.value = (byte) value;
    }

    @Override
    public int getValue() {
        return value;
    }

    /**
     * Converts ordinal number into enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static Hlvc fromOrdinal(int ordinal) {
        return EnumHelpers.get(Hlvc.class).fromOrdinal(ordinal);
    }
}
