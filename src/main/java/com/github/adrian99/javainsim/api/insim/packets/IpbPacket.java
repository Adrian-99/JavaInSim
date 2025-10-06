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
import com.github.adrian99.javainsim.api.insim.packets.structures.IPAddress;
import com.github.adrian99.javainsim.api.insim.packets.subtypes.tiny.TinySubtypes;
import com.github.adrian99.javainsim.internal.common.util.PacketDataBytes;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Array;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Unsigned;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;
import com.github.adrian99.javainsim.internal.insim.packets.base.InstructionPacket;
import com.github.adrian99.javainsim.internal.insim.packets.base.RequestablePacket;
import com.github.adrian99.javainsim.internal.insim.packets.exceptions.PacketValidationException;
import com.github.adrian99.javainsim.internal.insim.packets.requests.builders.SingleTinyPacketRequestBuilder;
import com.github.adrian99.javainsim.internal.insim.packets.util.Constants;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketBuilder;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketUtils;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketValidator;

import java.util.Arrays;
import java.util.List;

/**
 * IP Bans - variable size
 */
public class IpbPacket extends AbstractPacket implements InstructionPacket, RequestablePacket {
    @Unsigned
    @Array(length = Constants.IPB_MAX_BANS, dynamicLength = true)
    private final List<IPAddress> banIPs;

    /**
     * Creates IP bans packet. Constructor used only internally.
     * @param size packet size
     * @param reqI 0 unless this is a reply to a {@link TinySubtypes#IPB Tiny IPB} request
     * @param packetDataBytes packet data bytes
     */
    public IpbPacket(short size, short reqI, PacketDataBytes packetDataBytes) {
        super(size, PacketType.IPB, reqI);
        final var numB = packetDataBytes.readByte();
        packetDataBytes.skipZeroBytes(4);
        banIPs = Arrays.stream(packetDataBytes.readUnsignedArray(numB))
                .mapToObj(IPAddress::new)
                .toList();
    }

    /**
     * Creates IP bans packet.
     * @param banIPs IP addresses
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public IpbPacket(List<IPAddress> banIPs) throws PacketValidationException {
        super(
                PacketUtils.getPacketSize(8, banIPs.size(), 4, Constants.IPB_MAX_BANS),
                PacketType.IPB,
                0
        );
        this.banIPs = banIPs;

        PacketValidator.validate(this);
    }

    @Override
    public byte[] getBytes() {
        return new PacketBuilder(size, type, reqI)
                .writeByte(banIPs.size())
                .writeZeroBytes(4)
                .writeUnsignedArray(banIPs)
                .getBytes();
    }

    /**
     * @return number of bans in this packet
     */
    public short getNumB() {
        return (short) banIPs.size();
    }

    /**
     * @return IP addresses
     */
    public List<IPAddress> getBanIPs() {
        return banIPs;
    }

    /**
     * Creates builder for packet request for {@link IpbPacket}.
     * @param inSimConnection InSim connection to request packet from
     * @return packet request builder
     */
    public static SingleTinyPacketRequestBuilder<IpbPacket> request(InSimConnection inSimConnection) {
        return new SingleTinyPacketRequestBuilder<>(inSimConnection, TinySubtypes.IPB);
    }
}
