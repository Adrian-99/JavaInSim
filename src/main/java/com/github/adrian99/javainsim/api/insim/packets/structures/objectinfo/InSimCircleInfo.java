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
 * This class holds information about InSim circle.
 */
public class InSimCircleInfo extends ObjectInfo {
    /**
     * Creates InSim circle information. Constructor used only internally.
     * @param x X position (1 metre = 16)
     * @param y Y position (1 metre = 16)
     * @param zByte height (1m = 4)
     * @param flags object flags
     * @param heading heading
     */
    InSimCircleInfo(short x, short y, short zByte, short flags, short heading) {
        super(x, y, zByte, flags, ObjectType.MARSH_IS_AREA, heading);
    }

    /**
     * Creates InSim circle object information.
     * @param x X position (1 metre = 16)
     * @param y Y position (1 metre = 16)
     * @param zByte height (1m = 4)
     * @param isFloating whether object is floating
     * @param diameter diameter (2, 4, 6, 8, ..., 62)
     * @param circleIndex circle index
     */
    public InSimCircleInfo(int x, int y, int zByte, boolean isFloating, int diameter, int circleIndex) {
        super(
                (short) x,
                (short) y,
                (short) zByte,
                calculateFlagsValue(isFloating, diameter),
                ObjectType.MARSH_IS_AREA,
                (short) circleIndex
        );
    }

    /**
     * @return diameter (2, 4, 6, 8, ..., 62)
     */
    public byte getDiameter() {
        return (byte) ((flags >> 1) & 62);
    }

    /**
     * @return circle index (seen in the layout editor)
     */
    public short getCircleIndex() {
        return heading;
    }

    private static short calculateFlagsValue(boolean isFloating, int diameter) {
        return (short) (
                floatingBitForFlags(isFloating) |
                        normalizeIntValueForFlags(diameter, 2, 62, 1)
        );
    }
}
