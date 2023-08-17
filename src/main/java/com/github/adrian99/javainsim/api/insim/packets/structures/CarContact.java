/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.structures;

import com.github.adrian99.javainsim.api.common.flags.Flags;
import com.github.adrian99.javainsim.internal.common.util.PacketDataBytes;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Byte;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Char;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Short;
import com.github.adrian99.javainsim.api.insim.packets.flags.CompCarFlag;

/**
 * This class stores information about one car in a contact - there are two of these in the IS_CON.
 */
public class CarContact {
    @Byte
    private final short plid;
    @Byte
    private final Flags<CompCarFlag> info;
    @Char
    private final byte steer;
    @Byte
    private final short thrBrk;
    @Byte
    private final short cluHan;
    @Byte
    private final short gearSp;
    @Byte
    private final short speed;
    @Byte
    private final short direction;
    @Byte
    private final short heading;
    @Char
    private final byte accelF;
    @Char
    private final byte accelR;
    @Short
    private final short x;
    @Short
    private final short y;

    /**
     * Creates car contact information. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public CarContact(PacketDataBytes packetDataBytes) {
        plid = packetDataBytes.readByte();
        info = new Flags<>(CompCarFlag.class, packetDataBytes.readByte());
        packetDataBytes.skipZeroByte();
        steer = packetDataBytes.readChar();
        thrBrk = packetDataBytes.readByte();
        cluHan = packetDataBytes.readByte();
        gearSp = packetDataBytes.readByte();
        speed = packetDataBytes.readByte();
        direction = packetDataBytes.readByte();
        heading = packetDataBytes.readByte();
        accelF = packetDataBytes.readChar();
        accelR = packetDataBytes.readChar();
        x = packetDataBytes.readShort();
        y = packetDataBytes.readShort();
    }

    /**
     * @return player's unique id
     */
    public short getPlid() {
        return plid;
    }

    /**
     * @return flags and other info
     */
    public Flags<CompCarFlag> getInfo() {
        return info;
    }

    /**
     * @return front wheel steer in degrees (right positive)
     */
    public byte getSteer() {
        return steer;
    }

    /**
     * @return throttle (0 to 15)
     */
    public byte getThr() {
        return (byte) ((thrBrk >> 4) & 0xF);
    }

    /**
     * @return brake (0 to 15)
     */
    public byte getBrk() {
        return (byte) (thrBrk & 0xF);
    }

    /**
     * @return clutch (0 to 15)
     */
    public byte getClu() {
        return (byte) ((cluHan >> 4) & 0xF);
    }

    /**
     * @return handbrake (0 to 15)
     */
    public byte getHan() {
        return (byte) (cluHan & 0xF);
    }

    /**
     * @return gear (15=R)
     */
    public byte getGear() {
        return (byte) ((gearSp >> 4) & 0xF);
    }

    /**
     * @return speed (m/s)
     */
    public short getSpeed() {
        return speed;
    }

    /**
     * @return car's motion if Speed > 0: 0 = world y direction, 128 = 180 deg
     */
    public short getDirection() {
        return direction;
    }

    /**
     * @return direction of forward axis: 0 = world y direction, 128 = 180 deg
     */
    public short getHeading() {
        return heading;
    }

    /**
     * @return m/s^2 longitudinal acceleration (forward positive)
     */
    public byte getAccelF() {
        return accelF;
    }

    /**
     * @return m/s^2 lateral acceleration (right positive)
     */
    public byte getAccelR() {
        return accelR;
    }

    /**
     * @return X position (1 metre = 16)
     */
    public short getX() {
        return x;
    }

    /**
     * @return Y position (1 metre = 16)
     */
    public short getY() {
        return y;
    }
}
