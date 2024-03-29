/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.outsim.structures;

import com.github.adrian99.javainsim.internal.common.util.PacketDataBytes;

/**
 * Structure for distance.
 */
public class OutSimDistance {
    /**
     * Size of the structure.
     */
    public static final int SIZE = 8;

    private final float currentLapDist;
    private final float indexedDistance;

    /**
     * Creates structure for distance. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public OutSimDistance(PacketDataBytes packetDataBytes) {
        currentLapDist = packetDataBytes.readFloat();
        indexedDistance = packetDataBytes.readFloat();
    }

    /**
     * @return current lap distance - m travelled by car
     */
    public float getCurrentLapDist() {
        return currentLapDist;
    }

    /**
     * @return indexed distance - m track ruler measurement
     */
    public float getIndexedDistance() {
        return indexedDistance;
    }
}
