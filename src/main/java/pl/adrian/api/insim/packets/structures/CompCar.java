/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.insim.packets.structures;

import pl.adrian.api.insim.packets.flags.CompCarFlag;
import pl.adrian.api.common.flags.Flags;
import pl.adrian.internal.insim.packets.annotations.Byte;
import pl.adrian.internal.insim.packets.annotations.Int;
import pl.adrian.internal.insim.packets.annotations.Short;
import pl.adrian.internal.insim.packets.annotations.Word;
import pl.adrian.internal.common.util.PacketDataBytes;

/**
 * Car info in 28 bytes - there is an array of these in the MCI
 */
public class CompCar {
    @Word
    private final int node;
    @Word
    private final int lap;
    @Byte
    private final short plid;
    @Byte
    private final short position;
    @Byte
    private final Flags<CompCarFlag> info;
    @Int
    private final int x;
    @Int
    private final int y;
    @Int
    private final int z;
    @Word
    private final int speed;
    @Word
    private final int direction;
    @Word
    private final int heading;
    @Short
    private final short angVel;

    /**
     * Creates car information. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public CompCar(PacketDataBytes packetDataBytes) {
        node = packetDataBytes.readWord();
        lap = packetDataBytes.readWord();
        plid = packetDataBytes.readByte();
        position = packetDataBytes.readByte();
        info = new Flags<>(CompCarFlag.class, packetDataBytes.readByte());
        packetDataBytes.skipZeroByte();
        x = packetDataBytes.readInt();
        y = packetDataBytes.readInt();
        z = packetDataBytes.readInt();
        speed = packetDataBytes.readWord();
        direction = packetDataBytes.readWord();
        heading = packetDataBytes.readWord();
        angVel = packetDataBytes.readShort();
    }

    /**
     * @return current path node
     */
    public int getNode() {
        return node;
    }

    /**
     * @return current lap
     */
    public int getLap() {
        return lap;
    }

    /**
     * @return player's unique id
     */
    public short getPlid() {
        return plid;
    }

    /**
     * @return current race position: 0 = unknown, 1 = leader, etc...
     */
    public short getPosition() {
        return position;
    }

    /**
     * @return flags and other info
     */
    public Flags<CompCarFlag> getInfo() {
        return info;
    }

    /**
     * @return X map (65536 = 1 metre)
     */
    public int getX() {
        return x;
    }

    /**
     * @return Y map (65536 = 1 metre)
     */
    public int getY() {
        return y;
    }

    /**
     * @return Z alt (65536 = 1 metre)
     */
    public int getZ() {
        return z;
    }

    /**
     * @return speed (32768 = 100 m/s)
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * @return car's motion if Speed > 0: 0 = world y direction, 32768 = 180 deg
     */
    public int getDirection() {
        return direction;
    }

    /**
     * @return direction of forward axis: 0 = world y direction, 32768 = 180 deg, anticlockwise from above
     */
    public int getHeading() {
        return heading;
    }

    /**
     * @return signed, rate of change of heading: (16384 = 360 deg/s), anticlockwise
     */
    public short getAngVel() {
        return angVel;
    }
}
