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
import com.github.adrian99.javainsim.api.insim.packets.flags.IsiFlag;
import com.github.adrian99.javainsim.api.insim.packets.structures.CompCar;
import com.github.adrian99.javainsim.internal.common.util.PacketDataBytes;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Array;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Structure;
import com.github.adrian99.javainsim.internal.insim.packets.requests.builders.BasicTinyPacketRequestBuilder;
import com.github.adrian99.javainsim.internal.insim.packets.util.Constants;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;
import com.github.adrian99.javainsim.internal.insim.packets.base.RequestablePacket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Multi Car Info - if more than {@link Constants#MCI_MAX_CARS} in race then more than one is sent.
 * To receive this packet at a specified interval:<br>
 * 1) Set the Interval field in the {@link IsiPacket} packet (10, 20, 30... 8000 ms),<br>
 * 2) Set flag {@link IsiFlag#MCI Isi MCI} in the {@link IsiPacket}.
 */
public class MciPacket extends AbstractPacket implements RequestablePacket {
    @Structure
    @Array(length = Constants.MCI_MAX_CARS, dynamicLength = true)
    private final List<CompCar> info;

    /**
     * Creates multi car info packet.
     * @param size packet size
     * @param reqI 0 unless this is a reply to an {@link TinySubtype#MCI Tiny MCI} request
     * @param packetDataBytes pakcet data bytes
     */
    public MciPacket(short size, short reqI, PacketDataBytes packetDataBytes) {
        super(size, PacketType.MCI, reqI);
        var numC = packetDataBytes.readByte();
        var infoTmp = new ArrayList<CompCar>();
        for (var i = 0; i < numC; i++) {
            infoTmp.add(new CompCar(packetDataBytes));
        }
        info = Collections.unmodifiableList(infoTmp);
    }

    /**
     * @return number of valid {@link CompCar} structs in this packet
     */
    public short getNumC() {
        return (short) info.size();
    }

    /**
     * @return car info for each player
     */
    public List<CompCar> getInfo() {
        return info;
    }

    /**
     * Creates builder for packet request for {@link MciPacket}.
     * @param inSimConnection InSim connection to request packet from
     * @return packet request builder
     */
    public static BasicTinyPacketRequestBuilder<MciPacket> request(InSimConnection inSimConnection) {
        return new BasicTinyPacketRequestBuilder<>(inSimConnection, TinySubtype.MCI);
    }
}
