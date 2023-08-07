/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.outsim.structures;

import pl.adrian.internal.common.util.PacketDataBytes;

/**
 * Structure containing 3 floats x, y, z.
 */
public class Vector {
    private final float x;
    private final float y;
    private final float z;

    /**
     * Creates vector structure. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    Vector(PacketDataBytes packetDataBytes) {
        this.x = packetDataBytes.readFloat();
        this.y = packetDataBytes.readFloat();
        this.z = packetDataBytes.readFloat();
    }

    /**
     * @return X - right
     */
    public float getX() {
        return x;
    }

    /**
     * @return Y - forward
     */
    public float getY() {
        return y;
    }

    /**
     * @return Z - up
     */
    public float getZ() {
        return z;
    }
}
