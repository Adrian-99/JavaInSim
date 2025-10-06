/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.api.common.flags.Flags;
import com.github.adrian99.javainsim.api.common.structures.Car;
import com.github.adrian99.javainsim.api.insim.InSimConnection;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.insim.packets.flags.ConfirmationFlag;
import com.github.adrian99.javainsim.api.insim.packets.flags.PlayerFlag;
import com.github.adrian99.javainsim.api.insim.packets.subtypes.tiny.TinySubtypes;
import com.github.adrian99.javainsim.internal.common.util.PacketDataBytes;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.*;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Byte;
import com.github.adrian99.javainsim.internal.insim.packets.requests.builders.TinyPacketRequestBuilder;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;
import com.github.adrian99.javainsim.internal.insim.packets.base.RequestablePacket;

/**
 * RESult (qualify or confirmed finish).
 */
public class ResPacket extends AbstractPacket implements RequestablePacket {
    @Byte
    private final short plid;
    @Char
    @Array(length = 24)
    private final String uName;
    @Char
    @Array(length = 24)
    private final String pName;
    @Char
    @Array(length = 8)
    private final String plate;
    @Char
    @Array(length = 4)
    private final Car car;
    @Unsigned
    private final long tTime;
    @Unsigned
    private final long bTime;
    @Byte
    private final short numStops;
    @Byte
    private final Flags<ConfirmationFlag> confirm;
    @Word
    private final int lapsDone;
    @Word
    private final Flags<PlayerFlag> flags;
    @Byte
    private final short resultNum;
    @Byte
    private final short numRes;
    @Word
    private final int pSeconds;

    /**
     * Creates result packet. Constructor used only internally.
     * @param reqI 0 unless this is a reply to a {@link TinySubtypes#RES Tiny RES} request
     * @param packetDataBytes packet data bytes
     */
    public ResPacket(short reqI, PacketDataBytes packetDataBytes) {
        super(84, PacketType.RES, reqI);
        plid = packetDataBytes.readByte();
        uName = packetDataBytes.readCharArray(24);
        pName = packetDataBytes.readCharArray(24);
        plate = packetDataBytes.readCharArray(8);
        car = new Car(packetDataBytes);
        tTime = packetDataBytes.readUnsigned();
        bTime = packetDataBytes.readUnsigned();
        packetDataBytes.skipZeroByte();
        numStops = packetDataBytes.readByte();
        confirm = new Flags<>(ConfirmationFlag.class, packetDataBytes.readByte());
        packetDataBytes.skipZeroByte();
        lapsDone = packetDataBytes.readWord();
        flags = new Flags<>(PlayerFlag.class, packetDataBytes.readWord());
        resultNum = packetDataBytes.readByte();
        numRes = packetDataBytes.readByte();
        pSeconds = packetDataBytes.readWord();
    }

    /**
     * @return player's unique id (0 = player left before result was sent)
     */
    public short getPlid() {
        return plid;
    }

    /**
     * @return username
     */
    public String getUName() {
        return uName;
    }

    /**
     * @return nickname
     */
    public String getPName() {
        return pName;
    }

    /**
     * @return number plate
     */
    public String getPlate() {
        return plate;
    }

    /**
     * @return car
     */
    public Car getCar() {
        return car;
    }

    /**
     * @return (ms) race or autocross: total time / qualify: session time
     */
    public long getTTime() {
        return tTime;
    }

    /**
     * @return (ms) best lap
     */
    public long getBTime() {
        return bTime;
    }

    /**
     * @return number of pit stops
     */
    public short getNumStops() {
        return numStops;
    }

    /**
     * @return confirmation flags
     */
    public Flags<ConfirmationFlag> getConfirm() {
        return confirm;
    }

    /**
     * @return laps completed
     */
    public int getLapsDone() {
        return lapsDone;
    }

    /**
     * @return player flags
     */
    public Flags<PlayerFlag> getFlags() {
        return flags;
    }

    /**
     * @return finish or qualify pos (0 = win / 255 = not added to table)
     */
    public short getResultNum() {
        return resultNum;
    }

    /**
     * @return total number of results (qualify doesn't always add a new one)
     */
    public short getNumRes() {
        return numRes;
    }

    /**
     * @return penalty time in seconds (already included in race time)
     */
    public int getPSeconds() {
        return pSeconds;
    }

    /**
     * Creates builder for packet request for {@link ResPacket}.
     * @param inSimConnection InSim connection to request packet from
     * @return packet request builder
     */
    public static TinyPacketRequestBuilder<ResPacket> request(InSimConnection inSimConnection) {
        return new TinyPacketRequestBuilder<>(inSimConnection, TinySubtypes.RES);
    }
}
