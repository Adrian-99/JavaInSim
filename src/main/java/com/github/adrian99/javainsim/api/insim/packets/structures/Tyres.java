/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.structures;

import com.github.adrian99.javainsim.api.insim.packets.NplPacket;
import com.github.adrian99.javainsim.api.insim.packets.PitPacket;
import com.github.adrian99.javainsim.internal.common.util.PacketDataBytes;
import com.github.adrian99.javainsim.api.insim.packets.enums.TyreCompound;

/**
 * This class holds information about car tyres. In {@link NplPacket NplPacket} those are
 * types of tyres that player's car is equipped with. In {@link PitPacket PitPacket} those
 * are tyres that have been changed during pit stop.
 */
public class Tyres {
    private final TyreCompound rearLeft;
    private final TyreCompound rearRight;
    private final TyreCompound frontLeft;
    private final TyreCompound frontRight;

    /**
     * Creates tyres information. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public Tyres(PacketDataBytes packetDataBytes) {
        var unsignedValue = packetDataBytes.readUnsigned();
        rearLeft = TyreCompound.fromOrdinal((int) (unsignedValue & 0xFF));
        rearRight = TyreCompound.fromOrdinal((int) ((unsignedValue >> 8) & 0xFF));
        frontLeft = TyreCompound.fromOrdinal((int) ((unsignedValue >> 16) & 0xFF));
        frontRight = TyreCompound.fromOrdinal((int) ((unsignedValue >> 24) & 0xFF));
    }

    /**
     * @return compound of rear left tyre
     */
    public TyreCompound getRearLeft() {
        return rearLeft;
    }

    /**
     * @return compound of rear right tyre
     */
    public TyreCompound getRearRight() {
        return rearRight;
    }

    /**
     * @return compound of front left tyre
     */
    public TyreCompound getFrontLeft() {
        return frontLeft;
    }

    /**
     * @return compound of front right tyre
     */
    public TyreCompound getFrontRight() {
        return frontRight;
    }
}
