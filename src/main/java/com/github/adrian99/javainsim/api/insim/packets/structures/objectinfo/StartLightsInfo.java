/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.structures.objectinfo;

import com.github.adrian99.javainsim.api.insim.packets.enums.ObjectType;

/**
 * This class holds information about start lights object.
 */
public class StartLightsInfo extends AutocrossObjectInfo {
    /**
     * Creates start lights object information. Constructor used only internally.
     * @param x X position (1 metre = 16)
     * @param y Y position (1 metre = 16)
     * @param zByte height (1m = 4)
     * @param flags object flags
     * @param heading heading
     */
    StartLightsInfo(short x, short y, short zByte, short flags, short heading) {
        super(x, y, zByte, flags, ObjectType.START_LIGHTS, heading);
    }

    /**
     * Creates start lights object information.
     * @param x X position (1 metre = 16)
     * @param y Y position (1 metre = 16)
     * @param zByte height (1m = 4)
     * @param isFloating whether object is floating
     * @param identifier identifier
     * @param heading heading
     */
    public StartLightsInfo(int x, int y, int zByte, boolean isFloating, int identifier, int heading) {
        super(
                (short) x,
                (short) y,
                (short) zByte,
                calculateFlagsValue(isFloating, identifier),
                ObjectType.START_LIGHTS,
                (short) heading
        );
    }

    /**
     * @return identifier
     */
    public byte getIdentifier() {
        return (byte) (flags & 0x3F);
    }

    private static short calculateFlagsValue(boolean isFloating, int identifier) {
        return (short) (
                floatingBitForFlags(isFloating) |
                        normalizeIntValueForFlags(identifier, 0, 63, 0)
        );
    }
}
