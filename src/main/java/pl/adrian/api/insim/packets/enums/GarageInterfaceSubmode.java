/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.insim.packets.enums;

import pl.adrian.internal.common.enums.EnumHelpers;

/**
 * Enumeration for garage interface submodes used in {@link pl.adrian.api.insim.packets.CimPacket CimPacket}.
 */
public enum GarageInterfaceSubmode {
    /**
     * value 0: info tab
     */
    INFO,
    /**
     * value 1: colours tab
     */
    COLOURS,
    /**
     * value 2: brakes and traction control tab
     */
    BRAKE_TC,
    /**
     * value 3: suspension tab
     */
    SUSP,
    /**
     * value 4: steering tab
     */
    STEER,
    /**
     * value 5: drive tab
     */
    DRIVE,
    /**
     * value 6: tyres tab
     */
    TYRES,
    /**
     * value 7: aero tab
     */
    AERO,
    /**
     * value 8: passengers tab
     */
    PASS;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static GarageInterfaceSubmode fromOrdinal(int ordinal) {
        return EnumHelpers.get(GarageInterfaceSubmode.class).fromOrdinal(ordinal);
    }
}
