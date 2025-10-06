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
import com.github.adrian99.javainsim.api.insim.packets.flags.IsiFlag;
import com.github.adrian99.javainsim.api.insim.packets.structures.NodeLap;
import com.github.adrian99.javainsim.api.insim.packets.subtypes.tiny.TinySubtypes;
import com.github.adrian99.javainsim.internal.common.util.PacketDataBytes;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Array;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Structure;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;
import com.github.adrian99.javainsim.internal.insim.packets.requests.builders.SingleTinyPacketRequestBuilder;
import com.github.adrian99.javainsim.internal.insim.packets.util.Constants;
import com.github.adrian99.javainsim.internal.insim.packets.base.RequestablePacket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Node and Lap Packet - variable size. To receive this packet at a specified interval:<br>
 * 1) Set the Interval field in the {@link IsiPacket} packet (10, 20, 30... 8000 ms),<br>
 * 2) Set flag {@link IsiFlag#NLP Isi NLP} in the {@link IsiPacket}.
 */
public class NlpPacket extends AbstractPacket implements RequestablePacket {
    @Structure
    @Array(length = Constants.NLP_MAX_CARS, dynamicLength = true)
    private final List<NodeLap> info;

    /**
     * Creates node and lap packet. Constructor used only internally.
     * @param size packet size
     * @param reqI 0 unless this is a reply to a {@link TinySubtypes#NLP Tiny NLP} request
     * @param packetDataBytes packet data bytes
     */
    public NlpPacket(short size, short reqI, PacketDataBytes packetDataBytes) {
        super(size, PacketType.NLP, reqI);
        var numP = packetDataBytes.readByte();
        var infoTmp = new ArrayList<NodeLap>();
        for (var i = 0; i < numP; i++) {
            infoTmp.add(new NodeLap(packetDataBytes));
        }
        info = Collections.unmodifiableList(infoTmp);
    }

    /**
     * @return number of players in race
     */
    public short getNumP() {
        return (short) info.size();
    }

    /**
     * @return node and lap of each player
     */
    public List<NodeLap> getInfo() {
        return info;
    }

    /**
     * Creates builder for packet request for {@link NlpPacket}.
     * @param inSimConnection InSim connection to request packet from
     * @return packet request builder
     */
    public static SingleTinyPacketRequestBuilder<NlpPacket> request(InSimConnection inSimConnection) {
        return new SingleTinyPacketRequestBuilder<>(inSimConnection, TinySubtypes.NLP);
    }
}
