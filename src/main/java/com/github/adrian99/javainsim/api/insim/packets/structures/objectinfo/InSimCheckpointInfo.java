/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.structures.objectinfo;

import com.github.adrian99.javainsim.api.insim.packets.enums.InSimCheckpointType;
import com.github.adrian99.javainsim.api.insim.packets.enums.ObjectType;

/**
 * This class holds information about InSim checkpoint.
 */
public class InSimCheckpointInfo extends ObjectInfo {
    /**
     * Creates InSim checkpoint information. Constructor used only internally.
     * @param x X position (1 metre = 16)
     * @param y Y position (1 metre = 16)
     * @param zByte height (1m = 4)
     * @param flags object flags
     * @param heading heading
     */
    InSimCheckpointInfo(short x, short y, short zByte, short flags, short heading) {
        super(x, y, zByte, flags, ObjectType.MARSH_IS_CP, heading);
    }

    /**
     * Creates InSim checkpoint information.
     * @param x X position (1 metre = 16)
     * @param y Y position (1 metre = 16)
     * @param zByte height (1m = 4)
     * @param isFloating whether object is floating
     * @param checkpointIndex checkpoint index
     * @param width width (2, 4, 6, 8, ..., 62)
     * @param heading heading
     */
    public InSimCheckpointInfo(int x,
                               int y,
                               int zByte,
                               boolean isFloating,
                               InSimCheckpointType checkpointIndex,
                               int width,
                               int heading) {
        super(
                (short) x,
                (short) y,
                (short) zByte,
                calculateFlagsValue(isFloating, checkpointIndex, width),
                ObjectType.MARSH_IS_CP,
                (short) heading
        );
    }

    /**
     * @return checkpoint index (seen in the layout editor) - note that the checkpoint index has no meaning in LFS
     * and is provided only for user's convenience. If using many InSim checkpoints it may be needed to identify
     * them with the X and Y values.
     */
    public InSimCheckpointType getCheckpointIndex() {
        return InSimCheckpointType.fromOrdinal(flags & 3);
    }

    /**
     * @return width (2, 4, 6, 8, ..., 62)
     */
    public byte getWidth() {
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

    private static short calculateFlagsValue(boolean isFloating, InSimCheckpointType checkpointIndex, int width) {
        return (short) (
                floatingBitForFlags(isFloating) |
                        normalizeIntValueForFlags(width, 2, 62, 1) |
                        checkpointIndex.ordinal()
        );
    }
}
