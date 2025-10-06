/*
 * Copyright (c) 2024, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.api.insim.InSimConnection;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.insim.packets.structures.PlayerHandicaps;
import com.github.adrian99.javainsim.api.insim.packets.subtypes.tiny.TinySubtypes;
import com.github.adrian99.javainsim.internal.common.util.PacketDataBytes;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Array;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Structure;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;
import com.github.adrian99.javainsim.internal.insim.packets.base.InstructionPacket;
import com.github.adrian99.javainsim.internal.insim.packets.base.RequestablePacket;
import com.github.adrian99.javainsim.internal.insim.packets.exceptions.PacketValidationException;
import com.github.adrian99.javainsim.internal.insim.packets.requests.builders.SingleTinyPacketRequestBuilder;
import com.github.adrian99.javainsim.internal.insim.packets.util.Constants;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketBuilder;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketUtils;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketValidator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * PLayer Handicaps - variable size
 */
public class PlhPacket extends AbstractPacket implements InstructionPacket, RequestablePacket {
    @Structure
    @Array(length = Constants.PLH_MAX_PLAYERS, dynamicLength = true)
    private final List<PlayerHandicaps> hCaps;

    /**
     * Creates player handicaps packet. Constructor used only internally.
     * @param size packet size
     * @param reqI 0 unless this is a reply to a {@link TinySubtypes#PLH Tiny PLH} request
     * @param packetDataBytes packet data bytes
     */
    public PlhPacket(short size, short reqI, PacketDataBytes packetDataBytes) {
        super(size, PacketType.PLH, reqI);
        final var numP = packetDataBytes.readByte();
        var hCapsTmp = new ArrayList<PlayerHandicaps>();
        for (var i = 0; i < numP; i++) {
            hCapsTmp.add(new PlayerHandicaps(packetDataBytes));
        }
        hCaps = Collections.unmodifiableList(hCapsTmp);
    }

    /**
     * Creates player handicaps packet.
     * @param hCaps player handicaps
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public PlhPacket(List<PlayerHandicaps> hCaps) throws PacketValidationException {
        super(
                PacketUtils.getPacketSize(4, hCaps.size(), 4, Constants.PLH_MAX_PLAYERS),
                PacketType.PLH,
                0
        );
        this.hCaps = hCaps;

        PacketValidator.validate(this);
    }

    @Override
    public byte[] getBytes() {
        return new PacketBuilder(size, type, reqI)
                .writeByte(hCaps.size())
                .writeStructureArray(hCaps, 4)
                .getBytes();
    }

    /**
     * @return number of players in this packet
     */
    public short getNumP() {
        return (short) hCaps.size();
    }

    /**
     * @return player handicaps
     */
    public List<PlayerHandicaps> getHCaps() {
        return hCaps;
    }

    /**
     * Creates builder for packet request for {@link PlhPacket}.
     * @param inSimConnection InSim connection to request packet from
     * @return packet request builder
     */
    public static SingleTinyPacketRequestBuilder<PlhPacket> request(InSimConnection inSimConnection) {
        return new SingleTinyPacketRequestBuilder<>(inSimConnection, TinySubtypes.PLH);
    }
}
