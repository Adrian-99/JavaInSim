/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.structures.objectinfo;

import com.github.adrian99.javainsim.api.insim.packets.enums.ConcreteObjectColour;
import com.github.adrian99.javainsim.api.insim.packets.enums.ObjectType;

/**
 * This class holds information about concrete short slab wall object.
 */
public class ConcreteShortSlabWallInfo extends ConcreteObjectInfo {
    /**
     * Creates concrete short slab wall object information. Constructor used only internally.
     * @param x X position (1 metre = 16)
     * @param y Y position (1 metre = 16)
     * @param zByte height (1m = 4)
     * @param flags object flags
     * @param heading heading
     */
    ConcreteShortSlabWallInfo(short x, short y, short zByte, short flags, short heading) {
        super(x, y, zByte, flags, ObjectType.CONCRETE_SHORT_SLAB_WALL, heading);
    }

    /**
     * Creates concrete short slab wall object information.
     * @param x X position (1 metre = 16)
     * @param y Y position (1 metre = 16)
     * @param zByte height (1m = 4)
     * @param colour colour
     * @param sizeY size Y (0.25, 0.5, 0.75, 1)
     * @param pitch pitch (0 to 90 in steps of 6 degrees)
     * @param heading heading
     */
    public ConcreteShortSlabWallInfo(int x,
                                     int y,
                                     int zByte,
                                     ConcreteObjectColour colour,
                                     float sizeY,
                                     int pitch,
                                     int heading) {
        super(
                (short) x,
                (short) y,
                (short) zByte,
                (short) (convertColourToFlagsValue(colour) | convertSizeYToFlagsValue(sizeY) | convertPitchToFlagsValue(pitch)),
                ObjectType.CONCRETE_SHORT_SLAB_WALL,
                (short) heading
        );
    }

    /**
     * @return colour
     */
    public ConcreteObjectColour getColour() {
        return getColourInternal();
    }

    /**
     * @return size Y (0.25, 0.5, 0.75, 1)
     */
    public float getSizeY() {
        return getSizeYInternal();
    }

    /**
     * @return pitch (0 to 90 in steps of 6 degrees)
     */
    public byte getPitch() {
        return getPitchInternal();
    }
}
