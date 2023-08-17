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
 * Structure for wheel.
 */
public class OutSimWheel {
    /**
     * Size of the structure.
     */
    public static final int SIZE = 40;

    private final float suspDeflect;
    private final float steer;
    private final float xForce;
    private final float yForce;
    private final float verticalLoad;
    private final float angVel;
    private final float leanRelToRoad;
    private final short airTemp;
    private final short slipFraction;
    private final short touching;
    private final float slipRatio;
    private final float tanSlipAngle;

    /**
     * Creates structure for wheel. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public OutSimWheel(PacketDataBytes packetDataBytes) {
        suspDeflect = packetDataBytes.readFloat();
        steer = packetDataBytes.readFloat();
        xForce = packetDataBytes.readFloat();
        yForce = packetDataBytes.readFloat();
        verticalLoad = packetDataBytes.readFloat();
        angVel = packetDataBytes.readFloat();
        leanRelToRoad = packetDataBytes.readFloat();
        airTemp = packetDataBytes.readByte();
        slipFraction = packetDataBytes.readByte();
        touching = packetDataBytes.readByte();
        packetDataBytes.skipZeroByte();
        slipRatio = packetDataBytes.readFloat();
        tanSlipAngle = packetDataBytes.readFloat();
    }

    /**
     * @return compression from unloaded
     */
    public float getSuspDeflect() {
        return suspDeflect;
    }

    /**
     * @return steering - including Ackermann and toe
     */
    public float getSteer() {
        return steer;
    }

    /**
     * @return force right
     */
    public float getXForce() {
        return xForce;
    }

    /**
     * @return force forward
     */
    public float getYForce() {
        return yForce;
    }

    /**
     * @return vertical load - perpendicular to surface
     */
    public float getVerticalLoad() {
        return verticalLoad;
    }

    /**
     * @return angular velocity - radians/s
     */
    public float getAngVel() {
        return angVel;
    }

    /**
     * @return lean relative to road - radians a-c viewed from rear
     */
    public float getLeanRelToRoad() {
        return leanRelToRoad;
    }

    /**
     * @return air temperature - degrees C
     */
    public short getAirTemp() {
        return airTemp;
    }

    /**
     * @return slip fraction - 0 to 255
     */
    public short getSlipFraction() {
        return slipFraction;
    }

    /**
     * @return touching ground
     */
    public short getTouching() {
        return touching;
    }

    /**
     * @return slip ratio
     */
    public float getSlipRatio() {
        return slipRatio;
    }

    /**
     * @return tangent of slip angle
     */
    public float getTanSlipAngle() {
        return tanSlipAngle;
    }
}
