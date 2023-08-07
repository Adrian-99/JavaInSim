/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.insim.packets.structures.objectinfo;

import pl.adrian.api.insim.packets.enums.ConcreteObjectColour;
import pl.adrian.api.insim.packets.enums.ObjectType;

/**
 * This class holds information about concrete wedge object.
 */
public class ConcreteWedgeInfo extends ConcreteObjectInfo {
    /**
     * Creates concrete wedge object information. Constructor used only internally.
     * @param x X position (1 metre = 16)
     * @param y Y position (1 metre = 16)
     * @param zByte height (1m = 4)
     * @param flags object flags
     * @param heading heading
     */
    ConcreteWedgeInfo(short x, short y, short zByte, short flags, short heading) {
        super(x, y, zByte, flags, ObjectType.CONCRETE_WEDGE, heading);
    }

    /**
     * Creates concrete wedge object information.
     * @param x X position (1 metre = 16)
     * @param y Y position (1 metre = 16)
     * @param zByte height (1m = 4)
     * @param colour colour
     * @param length length (2, 4, 8, 16)
     * @param angle angle (5.625 to 90 in steps of 5.625 deg)
     * @param heading heading
     */
    public ConcreteWedgeInfo(int x, int y, int zByte, ConcreteObjectColour colour, int length, float angle, int heading) {
        super(
                (short) x,
                (short) y,
                (short) zByte,
                (short) (convertColourToFlagsValue(colour) | convertLengthToFlagsValue(length) | convertAngleToFlagsValue(angle)),
                ObjectType.CONCRETE_WEDGE,
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
     * @return length (2, 4, 8, 16)
     */
    public byte getLength() {
        return getLengthInternal();
    }

    /**
     * @return angle (5.625 to 90 in steps of 5.625 deg)
     */
    public float getAngle() {
        return getAngleInternal();
    }
}
