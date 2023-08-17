/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.outgauge;

import com.github.adrian99.javainsim.api.outgauge.flags.DashLight;
import com.github.adrian99.javainsim.api.outgauge.flags.OutGaugeFlag;
import com.github.adrian99.javainsim.internal.common.util.PacketDataBytes;
import com.github.adrian99.javainsim.api.common.flags.Flags;
import com.github.adrian99.javainsim.api.common.structures.Car;

/**
 * This class represents OutGauge packet sent by LFS.
 */
public class OutGaugePacket {
    /**
     * Size of the packet.
     */
    public static final int SIZE = 96;

    private final long time;
    private final Car car;
    private final Flags<OutGaugeFlag> flags;
    private final short gear;
    private final short plid;
    private final float speed;
    private final float rpm;
    private final float turbo;
    private final float engTemp;
    private final float fuel;
    private final float oilPressure;
    private final float oilTemp;
    private final Flags<DashLight> dashLights;
    private final Flags<DashLight> showLights;
    private final float throttle;
    private final float brake;
    private final float clutch;
    private final String display1;
    private final String display2;
    private final int id;

    OutGaugePacket(PacketDataBytes packetDataBytes) {
        time = packetDataBytes.readUnsigned();
        car = new Car(packetDataBytes);
        flags = new Flags<>(OutGaugeFlag.class, packetDataBytes.readWord());
        gear = packetDataBytes.readByte();
        plid = packetDataBytes.readByte();
        speed = packetDataBytes.readFloat();
        rpm = packetDataBytes.readFloat();
        turbo = packetDataBytes.readFloat();
        engTemp = packetDataBytes.readFloat();
        fuel = packetDataBytes.readFloat();
        oilPressure = packetDataBytes.readFloat();
        oilTemp = packetDataBytes.readFloat();
        dashLights = new Flags<>(DashLight.class, packetDataBytes.readUnsigned());
        showLights = new Flags<>(DashLight.class, packetDataBytes.readUnsigned());
        throttle = packetDataBytes.readFloat();
        brake = packetDataBytes.readFloat();
        clutch = packetDataBytes.readFloat();
        display1 = packetDataBytes.readCharArray(16);
        display2 = packetDataBytes.readCharArray(16);
        id = packetDataBytes.readInt();
    }

    /**
     * @return time in milliseconds (to check order)
     */
    public long getTime() {
        return time;
    }

    /**
     * @return car
     */
    public Car getCar() {
        return car;
    }

    /**
     * @return info
     */
    public Flags<OutGaugeFlag> getFlags() {
        return flags;
    }

    /**
     * @return gear - Reverse:0, Neutral:1, First:2...
     */
    public short getGear() {
        return gear;
    }

    /**
     * @return unique ID of viewed player (0 = none)
     */
    public short getPlid() {
        return plid;
    }

    /**
     * @return speed - M/S
     */
    public float getSpeed() {
        return speed;
    }

    /**
     * @return RPM
     */
    public float getRpm() {
        return rpm;
    }

    /**
     * @return turbo pressure - bar
     */
    public float getTurbo() {
        return turbo;
    }

    /**
     * @return engine temperature - C
     */
    public float getEngTemp() {
        return engTemp;
    }

    /**
     * @return fuel - 0 to 1
     */
    public float getFuel() {
        return fuel;
    }

    /**
     * @return oil pressure - bar
     */
    public float getOilPressure() {
        return oilPressure;
    }

    /**
     * @return oil temperature - C
     */
    public float getOilTemp() {
        return oilTemp;
    }

    /**
     * @return dash lights available
     */
    public Flags<DashLight> getDashLights() {
        return dashLights;
    }

    /**
     * @return dash lights currently switched on
     */
    public Flags<DashLight> getShowLights() {
        return showLights;
    }

    /**
     * @return throttle - 0 to 1
     */
    public float getThrottle() {
        return throttle;
    }

    /**
     * @return brake - 0 to 1
     */
    public float getBrake() {
        return brake;
    }

    /**
     * @return clutch - 0 to 1
     */
    public float getClutch() {
        return clutch;
    }

    /**
     * @return display 1 - usually fuel
     */
    public String getDisplay1() {
        return display1;
    }

    /**
     * @return display 2 - usually settings
     */
    public String getDisplay2() {
        return display2;
    }

    /**
     * @return optional - only if OutGauge ID is specified
     */
    public int getId() {
        return id;
    }
}
