/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.structures;

import com.github.adrian99.javainsim.internal.common.util.PacketDataBytes;
import com.github.adrian99.javainsim.api.insim.packets.enums.LapTimingType;

/**
 * This class helps to interpret lap timing info returned from LFS.
 */
public class LapTiming {
    private final LapTimingType type;
    private final short numberOfCheckpoints;

    /**
     * Creates new lap timing instance. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public LapTiming(PacketDataBytes packetDataBytes) {
        var byteValue = packetDataBytes.readByte();
        switch (byteValue & 0xc0) {
            case 0x80 -> type = LapTimingType.CUSTOM;
            case 0xc0 -> type = LapTimingType.NONE;
            default /*0x40*/ -> type = LapTimingType.STANDARD;
        }
        numberOfCheckpoints = (short) (byteValue & 0x03);
    }

    /**
     * @return lap timing type
     */
    public LapTimingType getType() {
        return type;
    }

    /**
     * @return number of checkpoints if lap timing is enabled
     */
    public short getNumberOfCheckpoints() {
        return numberOfCheckpoints;
    }
}
