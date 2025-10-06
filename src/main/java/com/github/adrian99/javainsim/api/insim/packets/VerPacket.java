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
import com.github.adrian99.javainsim.api.insim.packets.enums.Product;
import com.github.adrian99.javainsim.api.insim.packets.subtypes.tiny.TinySubtypes;
import com.github.adrian99.javainsim.internal.common.util.PacketDataBytes;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Array;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Byte;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Char;
import com.github.adrian99.javainsim.internal.insim.packets.requests.builders.SingleTinyPacketRequestBuilder;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;
import com.github.adrian99.javainsim.internal.insim.packets.base.RequestablePacket;

/**
 * VERsion - version info. This version packet is sent on request.
 */
public class VerPacket extends AbstractPacket implements RequestablePacket {
    @Char
    @Array(length = 8)
    private final String version;
    @Char
    @Array(length = 6)
    private final Product product;
    @Byte
    private final short inSimVer;

    /**
     * Creates version packet.
     * @param reqI 0 unless this is a reply to an {@link TinySubtypes#VER Tiny VER} request
     * @param packetDataBytes packet data bytes
     */
    public VerPacket(short reqI, PacketDataBytes packetDataBytes) {
        super(20, PacketType.VER, reqI);
        packetDataBytes.skipZeroByte();
        version = packetDataBytes.readCharArray(8);
        product = Product.fromString(packetDataBytes.readCharArray(6));
        inSimVer = packetDataBytes.readByte();
    }

    /**
     * @return LFS version, e.g. 0.3G
     */
    public String getVersion() {
        return version;
    }

    /**
     * @return LFS product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * @return InSim version
     */
    public short getInSimVer() {
        return inSimVer;
    }

    /**
     * Creates builder for packet request for {@link VerPacket}.
     * @param inSimConnection InSim connection to request packet from
     * @return packet request builder
     */
    public static SingleTinyPacketRequestBuilder<VerPacket> request(InSimConnection inSimConnection) {
        return new SingleTinyPacketRequestBuilder<>(inSimConnection, TinySubtypes.VER);
    }
}
