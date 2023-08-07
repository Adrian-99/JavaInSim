/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.insim.packets.structures.objectinfo;

import pl.adrian.api.insim.packets.enums.MarshallType;
import pl.adrian.api.insim.packets.enums.ObjectType;

/**
 * This class hold information about restricted area.
 */
public class RestrictedAreaInfo extends ObjectInfo {
    /**
     * Creates restricted area information. Constructor used only internally.
     * @param x X position (1 metre = 16)
     * @param y Y position (1 metre = 16)
     * @param zByte height (1m = 4)
     * @param flags object flags
     * @param heading heading
     */
    RestrictedAreaInfo(short x, short y, short zByte, short flags, short heading) {
        super(x, y, zByte, flags, ObjectType.MARSH_MARSHAL, heading);
    }

    /**
     * Creates restricted area information.
     * @param x X position (1 metre = 16)
     * @param y Y position (1 metre = 16)
     * @param zByte height (1m = 4)
     * @param isFloating whether object is floating
     * @param marshallType marshall type
     * @param diameter diameter (2, 4, 6, 8, ..., 62)
     * @param heading heading
     */
    public RestrictedAreaInfo(int x,
                              int y,
                              int zByte,
                              boolean isFloating,
                              MarshallType marshallType,
                              int diameter,
                              int heading) {
        super(
                (short) x,
                (short) y,
                (short) zByte,
                calculateFlagsValue(isFloating, marshallType, diameter),
                ObjectType.MARSH_MARSHAL,
                (short) heading
        );
    }

    /**
     * @return marshall type
     */
    public MarshallType getMarshallType() {
        return MarshallType.fromOrdinal(flags & 3);
    }

    /**
     * @return diameter (2, 4, 6, 8, ..., 62)
     */
    public byte getDiameter() {
        return (byte) ((flags >> 1) & 62);
    }

    /**
     * @return heading - 360 degrees in 256 values:<br>
     *  - 128 : heading of zero<br>
     *  - 192 : heading of 90 degrees<br>
     *  - 0   : heading of 180 degrees<br>
     *  - 64  : heading of -90 degrees
     */
    public short getHeading() {
        return heading;
    }

    private static short calculateFlagsValue(boolean isFloating, MarshallType marshallType, int diameter) {
        return (short) (
                floatingBitForFlags(isFloating) |
                        marshallType.ordinal() |
                        normalizeIntValueForFlags(diameter, 2, 62, 1)
        );
    }
}
