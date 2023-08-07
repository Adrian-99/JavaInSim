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
 * Abstract class inherited by all classes that represent information about
 * concrete objects.
 */
public abstract class ConcreteObjectInfo extends AutocrossObjectInfo {
    /**
     * Creates concrete object information. Constructor used only internally.
     * @param x X position (1 metre = 16)
     * @param y Y position (1 metre = 16)
     * @param zByte height (1m = 4)
     * @param flags object flags
     * @param index object index
     * @param heading heading
     */
    protected ConcreteObjectInfo(short x, short y, short zByte, short flags, ObjectType index, short heading) {
        super(x, y, zByte, flags, index, heading);
    }

    @Override
    public boolean isFloating() {
        return true;
    }

    /**
     * @return width (2, 4, 8, 16)
     */
    protected byte getWidthInternal() {
        return (byte) (2 << (flags & 3));
    }

    /**
     * @return length (2, 4, 8, 16)
     */
    protected byte getLengthInternal() {
        return (byte) (2 << ((flags & 0xC) >> 2));
    }

    /**
     * @return size X (0.25, 0.5, 0.75, 1)
     */
    protected float getSizeXInternal() {
        return ((flags & 3) + 1) / 4.0f;
    }

    /**
     * @return size Y (0.25, 0.5, 0.75, 1)
     */
    protected float getSizeYInternal() {
        return (((flags & 0xC) >> 2) + 1) / 4.0f;
    }

    /**
     * @return height (0.25 to 4 in steps of 0.25)
     */
    protected float getHeightInternal() {
        return (((flags & 0xF0) >> 4) + 1) / 4.0f;
    }

    /**
     * @return pitch (0 to 90 in steps of 6 degrees)
     */
    protected byte getPitchInternal() {
        return (byte) (((flags & 0xF0) >> 4) * 6);
    }

    /**
     * @return colour
     */
    protected ConcreteObjectColour getColourInternal() {
        return ConcreteObjectColour.fromOrdinal(flags & 3);
    }

    /**
     * @return angle (5.625 to 90 in steps of 5.625 deg)
     */
    protected float getAngleInternal() {
        return (((flags & 0xF0) >> 4) + 1) * 5.625f;
    }

    /**
     * Converts width to flags value.
     * @param width width
     * @return width value for flags
     */
    protected static int convertWidthToFlagsValue(int width) {
        return convertWidthAndLength(width);
    }

    /**
     * Converts length to flags value.
     * @param length length
     * @return length value for flags
     */
    protected static int convertLengthToFlagsValue(int length) {
        return convertWidthAndLength(length) << 2;
    }

    /**
     * Converts size X to flags value.
     * @param sizeX size X
     * @return size X value for flags
     */
    protected static int convertSizeXToFlagsValue(float sizeX) {
        return convertFloat(sizeX, 0.25f, 3);
    }

    /**
     * Converts size Y to flags value.
     * @param sizeY size Y
     * @return size Y value for flags
     */
    protected static int convertSizeYToFlagsValue(float sizeY) {
        return convertFloat(sizeY, 0.25f, 3) << 2;
    }

    /**
     * Converts height to flags value.
     * @param height height
     * @return height value for flags
     */
    protected static int convertHeightToFlagsValue(float height) {
        return convertFloat(height, 0.25f, 15) << 4;
    }

    /**
     * Converts pitch to flags value.
     * @param pitch pitch
     * @return pitch value for flags
     */
    protected static int convertPitchToFlagsValue(int pitch) {
        if (pitch >= 90) {
            return 15 << 4;
        } else if (pitch <= 0) {
            return 0;
        } else {
            return (pitch / 6) << 4;
        }
    }

    /**
     * Converts colour to flags value.
     * @param colour colour
     * @return colour value for flags
     */
    protected static int convertColourToFlagsValue(ConcreteObjectColour colour) {
        return colour.ordinal();
    }

    /**
     * Converts angle to flags value.
     * @param angle angle
     * @return angle value for flags
     */
    protected static int convertAngleToFlagsValue(float angle) {
        return convertFloat(angle, 5.625f, 15) << 4;
    }

    private static int convertWidthAndLength(int value) {
        var valueNormalized = normalizeIntValueForFlags(value, 2, 16, 31, 0);
        if ((valueNormalized & 16) == 16) {
            return 3;
        } else if ((valueNormalized & 8) == 8) {
            return 2;
        } else if ((valueNormalized & 4) == 4) {
            return 1;
        } else {
            return 0;
        }
    }

    private static int convertFloat(float value, float step, int maxResult) {
        return Math.min(maxResult, Math.max(0, Math.round(value / step) - 1));
    }
}
