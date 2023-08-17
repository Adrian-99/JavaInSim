/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.api.insim.InSimConnection;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.insim.packets.enums.TinySubtype;
import com.github.adrian99.javainsim.internal.common.util.PacketDataBytes;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Array;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Byte;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Char;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;
import com.github.adrian99.javainsim.internal.insim.packets.requests.builders.SingleTinyPacketRequestBuilder;
import com.github.adrian99.javainsim.internal.insim.packets.base.RequestablePacket;

/**
 * InSim Multi. The packet is sent by LFS when a host is started or joined.
 */
public class IsmPacket extends AbstractPacket implements RequestablePacket {
    @Byte
    private final boolean isHost;
    @Char
    @Array(length = 32)
    private final String hName;

    /**
     * Creates InSim multi packet. Constructor used only internally.
     * @param reqI usually 0 / or if a reply: ReqI as received in the {@link TinySubtype#ISM Tiny ISM}
     * @param packetDataBytes packet data bytes
     */
    public IsmPacket(short reqI, PacketDataBytes packetDataBytes) {
        super(40, PacketType.ISM, reqI);
        packetDataBytes.skipZeroByte();
        isHost = packetDataBytes.readByte() != 0;
        packetDataBytes.skipZeroBytes(3);
        hName = packetDataBytes.readCharArray(32);
    }

    /**
     * @return whether is a host
     */
    public boolean isHost() {
        return isHost;
    }

    /**
     * @return the name of the host joined or started
     */
    public String getHName() {
        return hName;
    }

    /**
     * Creates builder for packet request for {@link IsmPacket}.
     * @param inSimConnection InSim connection to request packet from
     * @return packet request builder
     */
    public static SingleTinyPacketRequestBuilder<IsmPacket> request(InSimConnection inSimConnection) {
        return new SingleTinyPacketRequestBuilder<>(inSimConnection, TinySubtype.ISM);
    }
}
