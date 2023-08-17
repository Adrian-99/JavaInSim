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
 * This class holds information about concrete ramp object.
 */
public class ConcreteRampInfo extends ConcreteObjectInfo {
    /**
     * Creates concrete ramp object information. Constructor used only internally.
     * @param x X position (1 metre = 16)
     * @param y Y position (1 metre = 16)
     * @param zByte height (1m = 4)
     * @param flags object flags
     * @param heading heading
     */
    ConcreteRampInfo(short x, short y, short zByte, short flags, short heading) {
        super(x, y, zByte, flags, ObjectType.CONCRETE_RAMP, heading);
    }

    /**
     * Creates concrete ramp object information.
     * @param x X position (1 metre = 16)
     * @param y Y position (1 metre = 16)
     * @param zByte height (1m = 4)
     * @param width width (2, 4, 8, 16)
     * @param length length (2, 4, 8, 16)
     * @param height height (0.25 to 4 in steps of 0.25)
     * @param heading heading
     */
    public ConcreteRampInfo(int x, int y, int zByte, int width, int length, float height, int heading) {
        super (
                (short) x,
                (short) y,
                (short) zByte,
                (short) (convertWidthToFlagsValue(width) | convertLengthToFlagsValue(length) | convertHeightToFlagsValue(height)),
                ObjectType.CONCRETE_RAMP,
                (short) heading
        );
    }

    /**
     * @return width (2, 4, 8, 16)
     */
    public byte getWidth() {
        return getWidthInternal();
    }

    /**
     * @return length (2, 4, 8, 16)
     */
    public byte getLength() {
        return getLengthInternal();
    }

    /**
     * @return height (0.25 to 4 in steps of 0.25)
     */
    public float getHeight() {
        return getHeightInternal();
    }
}
