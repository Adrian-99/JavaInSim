/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.api.common.flags.Flags;
import com.github.adrian99.javainsim.api.insim.InSimConnection;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.insim.packets.enums.TinySubtype;
import com.github.adrian99.javainsim.api.insim.packets.flags.NcnFlag;
import com.github.adrian99.javainsim.internal.common.util.PacketDataBytes;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Array;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Byte;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Char;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;
import com.github.adrian99.javainsim.internal.insim.packets.requests.builders.BasicTinyPacketRequestBuilder;
import com.github.adrian99.javainsim.internal.insim.packets.requests.builders.SingleTinyPacketRequestBuilder;
import com.github.adrian99.javainsim.internal.insim.packets.base.RequestablePacket;

/**
 * New ConN. The packet is sent by LFS when there is a new connection.
 */
public class NcnPacket extends AbstractPacket implements RequestablePacket {
    @Byte
    private final short ucid;
    @Char
    @Array(length = 24)
    private final String uName;
    @Char
    @Array(length = 24)
    private final String pName;
    @Byte
    private final boolean isAdmin;
    @Byte
    private final short total;
    @Byte
    private final Flags<NcnFlag> flags;

    /**
     * Creates new connection packet. Constructor used only internally.
     * @param reqI 0 unless this is a reply to an {@link TinySubtype#NCN Tiny NCN} request
     * @param packetDataBytes packet data bytes
     */
    public NcnPacket(short reqI, PacketDataBytes packetDataBytes) {
        super(56, PacketType.NCN, reqI);
        ucid = packetDataBytes.readByte();
        uName = packetDataBytes.readCharArray(24);
        pName = packetDataBytes.readCharArray(24);
        isAdmin = packetDataBytes.readByte() == 1;
        total = packetDataBytes.readByte();
        flags = new Flags<>(NcnFlag.class, packetDataBytes.readByte());
        packetDataBytes.skipZeroByte();
    }

    /**
     * @return new connection's unique id (0 = host)
     */
    public short getUcid() {
        return ucid;
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
     * @return whether is an admin
     */
    public boolean isAdmin() {
        return isAdmin;
    }

    /**
     * @return number of connections including host
     */
    public short getTotal() {
        return total;
    }

    /**
     * @return flags
     */
    public Flags<NcnFlag> getFlags() {
        return flags;
    }

    /**
     * Creates builder for packet request for {@link NcnPacket}.
     * @param inSimConnection InSim connection to request packet from
     * @return packet request builder
     */
    public static BasicTinyPacketRequestBuilder<NcnPacket> request(InSimConnection inSimConnection) {
        return new SingleTinyPacketRequestBuilder<>(inSimConnection, TinySubtype.NCN);
    }
}
