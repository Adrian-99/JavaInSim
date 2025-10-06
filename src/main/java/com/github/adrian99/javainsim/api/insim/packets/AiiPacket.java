/*
 * Copyright (c) 2025, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.api.common.flags.Flags;
import com.github.adrian99.javainsim.api.insim.InSimConnection;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.insim.packets.flags.AiiFlag;
import com.github.adrian99.javainsim.api.outgauge.flags.DashLight;
import com.github.adrian99.javainsim.api.outsim.structures.OutSimMain;
import com.github.adrian99.javainsim.internal.common.util.PacketDataBytes;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Byte;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Float;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Structure;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Unsigned;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;
import com.github.adrian99.javainsim.internal.insim.packets.base.RequestablePacket;
import com.github.adrian99.javainsim.internal.insim.packets.requests.builders.AiiPacketRequestBuilder;

/**
 * AI Info.
 */
public class AiiPacket extends AbstractPacket implements RequestablePacket {
    @Byte
    private final short plid;
    @Structure
    private final OutSimMain osData;
    @Byte
    private final Flags<AiiFlag> flags;
    @Byte
    private final short gear;
    @Float
    private final float rpm;
    @Unsigned
    private final Flags<DashLight> showLights;

    /**
     * Creates AI info packet. Constructor used only internally.
     * @param reqI ReqI from request packet
     * @param packetDataBytes packet data bytes
     */
    public AiiPacket(short reqI, PacketDataBytes packetDataBytes) {
        super(96, PacketType.AII, reqI);
        plid = packetDataBytes.readByte();
        osData = new OutSimMain(packetDataBytes);
        flags = new Flags<>(AiiFlag.class, packetDataBytes.readByte());
        gear = packetDataBytes.readByte();
        packetDataBytes.skipZeroBytes(2);
        rpm = packetDataBytes.readFloat();
        packetDataBytes.skipZeroBytes(8);
        showLights = new Flags<>(DashLight.class, packetDataBytes.readUnsigned());
        packetDataBytes.skipZeroBytes(12);
    }

    /**
     * @return unique id of AI driver
     */
    public short getPlid() {
        return plid;
    }

    /**
     * @return OutSim data
     */
    public OutSimMain getOsData() {
        return osData;
    }

    /**
     * @return AI info flags
     */
    public Flags<AiiFlag> getFlags() {
        return flags;
    }

    /**
     * @return gear (Reverse:0, Neutral:1, First:2...)
     */
    public short getGear() {
        return gear;
    }

    /**
     * @return RPM
     */
    public float getRpm() {
        return rpm;
    }

    /**
     * @return dash lights currently switched on
     */
    public Flags<DashLight> getShowLights() {
        return showLights;
    }

    /**
     * Creates builder for packet request for {@link AiiPacket}.
     * @param inSimConnection InSim connection to request packet from
     * @param plid unique id of AI driver
     * @return packet request builder
     */
    public static AiiPacketRequestBuilder request(InSimConnection inSimConnection, int plid) {
        return new AiiPacketRequestBuilder(inSimConnection, (short) plid);
    }
}
