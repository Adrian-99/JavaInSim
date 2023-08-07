/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.insim.packets.structures.objectinfo;

import pl.adrian.api.insim.packets.enums.ObjectType;

/**
 * This class hold information about position (not necessarily associated with an object).
 */
public class PositionObjectInfo extends ObjectInfo {
    /**
     * Creates position information.
     * @param x X position (1 metre = 16)
     * @param y Y position (1 metre = 16)
     * @param zByte height (1m = 4)
     * @param heading heading
     */
    public PositionObjectInfo(int x, int y, int zByte, int heading) {
        super((short) x, (short) y, (short) zByte, (short) 0x80, ObjectType.NULL, (short) heading);
    }

    /**
     * Creates position information.
     * @param x X position (1 metre = 16)
     * @param y Y position (1 metre = 16)
     * @param zByte height (1m = 4)
     */
    public PositionObjectInfo(int x, int y, int zByte) {
        this(x, y, zByte, 0);
    }
}
