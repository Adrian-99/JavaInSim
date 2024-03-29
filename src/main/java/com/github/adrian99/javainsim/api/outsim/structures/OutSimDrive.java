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
 * Structure for drive.
 */
public class OutSimDrive {
    /**
     * Size of the structure.
     */
    public static final int SIZE = 12;

    private final short gear;
    private final float engineAngVel;
    private final float maxTorqueAtVel;

    /**
     * Creates structure for drive.
     * @param packetDataBytes packet data bytes
     */
    public OutSimDrive(PacketDataBytes packetDataBytes) {
        gear = packetDataBytes.readByte();
        packetDataBytes.skipZeroBytes(3);
        engineAngVel = packetDataBytes.readFloat();
        maxTorqueAtVel = packetDataBytes.readFloat();
    }

    /**
     * @return gear - 0=R, 1=N, 2=first gear
     */
    public short getGear() {
        return gear;
    }

    /**
     * @return engine angular velocity - radians/s
     */
    public float getEngineAngVel() {
        return engineAngVel;
    }

    /**
     * @return maximum torque at velocity - Nm : output torque for throttle 1.0
     */
    public float getMaxTorqueAtVel() {
        return maxTorqueAtVel;
    }
}
