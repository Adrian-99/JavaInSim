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
import com.github.adrian99.javainsim.api.insim.packets.enums.TinySubtype;
import com.github.adrian99.javainsim.api.insim.packets.flags.*;
import com.github.adrian99.javainsim.api.insim.packets.structures.Tyres;
import com.github.adrian99.javainsim.internal.common.util.PacketDataBytes;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Array;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Byte;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Char;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Word;
import com.github.adrian99.javainsim.internal.insim.packets.requests.builders.BasicTinyPacketRequestBuilder;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;
import com.github.adrian99.javainsim.internal.insim.packets.base.RequestablePacket;

/**
 * New PLayer joining race. The packet is sent by LFS when player joins race
 * (if plid already exists, then leaving pits).<br>
 * If flag {@link IsiFlag#REQ_JOIN} was set in {@link IsiPacket}, then join
 * requests are sent by LFS in form of {@link NplPacket} with zero in the {@link NplPacket#numP}.
 * field. An immediate response (e.g. within 1 second) is required using a {@link JrrPacket}.
 */
public class NplPacket extends AbstractPacket implements RequestablePacket {
    @Byte
    private final short plid;
    @Byte
    private final short ucid;
    @Byte
    private final Flags<PlayerTypeFlag> pType;
    @Word
    private final Flags<PlayerFlag> flags;
    @Char
    @Array(length = 24)
    private final String pName;
    @Char
    @Array(length = 8)
    private final String plate;
    @Char
    @Array(length = 4)
    private final Car car;
    @Char
    @Array(length = 16)
    private final String sName;
    @Byte
    @Array(length = 4)
    private final Tyres tyres;
    @Byte
    private final short hMass;
    @Byte
    private final short hTRes;
    @Byte
    private final short model;
    @Byte
    private final Flags<PassengerFlag> pass;
    @Byte
    private final short rWAdj;
    @Byte
    private final short fWAdj;
    @Byte
    private final Flags<SetupFlag> setF;
    @Byte
    private final short numP;
    @Byte
    private final short config;
    @Byte
    private final short fuel;

    /**
     * Creates new player joining race packet. Constructor used only internally.
     * @param reqI 0 unless this is a reply to an {@link TinySubtype#NPL Tiny NPL} request
     * @param packetDataBytes packet data bytes
     */
    public NplPacket(short reqI, PacketDataBytes packetDataBytes) {
        super(76, PacketType.NPL, reqI);
        plid = packetDataBytes.readByte();
        ucid = packetDataBytes.readByte();
        pType = new Flags<>(PlayerTypeFlag.class, packetDataBytes.readByte());
        flags = new Flags<>(PlayerFlag.class, packetDataBytes.readWord());
        pName = packetDataBytes.readCharArray(24);
        plate = packetDataBytes.readCharArray(8);
        car = new Car(packetDataBytes);
        sName = packetDataBytes.readCharArray(16);
        tyres = new Tyres(packetDataBytes);
        hMass = packetDataBytes.readByte();
        hTRes = packetDataBytes.readByte();
        model = packetDataBytes.readByte();
        pass = new Flags<>(PassengerFlag.class, packetDataBytes.readByte());
        rWAdj = packetDataBytes.readByte();
        fWAdj = packetDataBytes.readByte();
        packetDataBytes.skipZeroBytes(2);
        setF = new Flags<>(SetupFlag.class, packetDataBytes.readByte());
        numP = packetDataBytes.readByte();
        config = packetDataBytes.readByte();
        fuel = packetDataBytes.readByte();
    }

    /**
     * @return player's newly assigned unique id
     */
    public short getPlid() {
        return plid;
    }

    /**
     * @return connection's unique id
     */
    public short getUcid() {
        return ucid;
    }

    /**
     * NOTE: PType bit 0 (female) is not reported on dedicated host as humans are not loaded.
     * You can use the driver model byte instead if required (and to force the use of helmets).
     * @return player type flags
     */
    public Flags<PlayerTypeFlag> getPType() {
        return pType;
    }

    /**
     * @return player flags
     */
    public Flags<PlayerFlag> getFlags() {
        return flags;
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
     * @return skin name
     */
    public String getSName() {
        return sName;
    }

    /**
     * @return compounds
     */
    public Tyres getTyres() {
        return tyres;
    }

    /**
     * @return added mass (kg)
     */
    public short getHMass() {
        return hMass;
    }

    /**
     * @return intake restriction
     */
    public short getHTRes() {
        return hTRes;
    }

    /**
     * @return driver model
     */
    public short getModel() {
        return model;
    }

    /**
     * @return passenger flags
     */
    public Flags<PassengerFlag> getPass() {
        return pass;
    }

    /**
     * @return low 4 bits: tyre width reduction (rear)
     */
    public short getRWAdj() {
        return rWAdj;
    }

    /**
     * @return low 4 bits: tyre width reduction (front)
     */
    public short getFWAdj() {
        return fWAdj;
    }

    /**
     * @return setup flags
     */
    public Flags<SetupFlag> getSetF() {
        return setF;
    }

    /**
     * @return number in race - ZERO if this is a join request
     */
    public short getNumP() {
        return numP;
    }

    /**
     * @return car configuration
     */
    public short getConfig() {
        return config;
    }

    /**
     * @return fuel percent (if /showfuel yes), 255 otherwise
     */
    public short getFuel() {
        return fuel;
    }

    /**
     * Creates builder for packet request for {@link NplPacket}.
     * @param inSimConnection InSim connection to request packet from
     * @return packet request builder
     */
    public static BasicTinyPacketRequestBuilder<NplPacket> request(InSimConnection inSimConnection) {
        return new BasicTinyPacketRequestBuilder<>(inSimConnection, TinySubtype.NPL);
    }
}
