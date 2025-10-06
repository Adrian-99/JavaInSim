/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.api.insim.InSimConnection;
import com.github.adrian99.javainsim.api.insim.packets.subtypes.tiny.TinySubtypes;
import com.github.adrian99.javainsim.internal.common.util.PacketDataBytes;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Array;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Byte;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Char;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Word;
import com.github.adrian99.javainsim.internal.insim.packets.requests.builders.SingleTinyPacketRequestBuilder;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;
import com.github.adrian99.javainsim.internal.insim.packets.base.RequestablePacket;

/**
 * AutoX Info. The packet is sent when a layout is loaded.
 */
public class AxiPacket extends AbstractPacket implements RequestablePacket {
    @Byte
    private final short aXStart;
    @Byte
    private final short numCP;
    @Word
    private final int numO;
    @Char
    @Array(length = 32)
    private final String lName;

    /**
     * Creates autoX info packet. Constructor used only internally.
     * @param reqI 0 unless this is a reply to an {@link TinySubtypes#AXI Tiny AXI} request
     * @param packetDataBytes packet data bytes
     */
    public AxiPacket(short reqI, PacketDataBytes packetDataBytes) {
        super(40, PacketType.AXI, reqI);
        packetDataBytes.skipZeroByte();
        aXStart = packetDataBytes.readByte();
        numCP = packetDataBytes.readByte();
        numO = packetDataBytes.readWord();
        lName = packetDataBytes.readCharArray(32);
    }

    /**
     * @return autocross start position
     */
    public short getAXStart() {
        return aXStart;
    }

    /**
     * @return number of checkpoints
     */
    public short getNumCP() {
        return numCP;
    }

    /**
     * @return number of objects
     */
    public int getNumO() {
        return numO;
    }

    /**
     * @return the name of the layout last loaded (if loaded locally)
     */
    public String getLName() {
        return lName;
    }

    /**
     * Creates builder for packet request for {@link AxiPacket}.
     * @param inSimConnection InSim connection to request packet from
     * @return packet request builder
     */
    public static SingleTinyPacketRequestBuilder<AxiPacket> request(InSimConnection inSimConnection) {
        return new SingleTinyPacketRequestBuilder<>(inSimConnection, TinySubtypes.AXI);
    }
}
