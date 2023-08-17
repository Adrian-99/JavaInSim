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
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Unsigned;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;
import com.github.adrian99.javainsim.internal.insim.packets.base.InstructionPacket;
import com.github.adrian99.javainsim.internal.insim.packets.exceptions.PacketValidationException;
import com.github.adrian99.javainsim.internal.insim.packets.requests.builders.SingleTinyPacketRequestBuilder;
import com.github.adrian99.javainsim.internal.insim.packets.structures.ModSkinId;
import com.github.adrian99.javainsim.internal.insim.packets.util.Constants;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketBuilder;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketUtils;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketValidator;
import com.github.adrian99.javainsim.internal.insim.packets.base.RequestablePacket;

import java.util.Arrays;
import java.util.List;

/**
 * Mods ALlowed - variable size.
 */
public class MalPacket extends AbstractPacket implements InstructionPacket, RequestablePacket {
    @Byte
    private final short ucid;
    @Unsigned
    @Array(length = Constants.MAL_MAX_MODS, dynamicLength = true)
    private final List<ModSkinId> skinId;

    /**
     * Creates mods allowed packet. Constructor used only internally.
     * @param size packet size
     * @param reqI 0 unless this is a reply to a {@link TinySubtype#MAL Tiny MAL} request
     * @param packetDataBytes packet data bytes
     */
    public MalPacket(short size, short reqI, PacketDataBytes packetDataBytes) {
        super(size, PacketType.MAL, reqI);
        final var numM = packetDataBytes.readByte();
        ucid = packetDataBytes.readByte();
        packetDataBytes.skipZeroBytes(3);
        skinId = Arrays.stream(packetDataBytes.readUnsignedArray(numM)).mapToObj(ModSkinId::new).toList();
    }

    /**
     * Creates mods allowed packet.
     * @param skinId list of skinIds of allowed mods (empty list to allow all mods to be used)
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public MalPacket(List<String> skinId) throws PacketValidationException {
        super(
                PacketUtils.getPacketSize(8, skinId.size(), 4, Constants.MAL_MAX_MODS),
                PacketType.MAL,
                0
        );
        this.ucid = 0;
        this.skinId = skinId.stream().map(ModSkinId::new).toList();

        PacketValidator.validate(this);
    }

    @Override
    public byte[] getBytes() {
        return new PacketBuilder(size, type, reqI)
                .writeByte(skinId.size())
                .writeByte(ucid)
                .writeZeroBytes(3)
                .writeUnsignedArray(skinId)
                .getBytes();
    }

    /**
     * @return number of mods in this packet
     */
    public short getNumM() {
        return (short) skinId.size();
    }

    /**
     * @return unique id of the connection that updated the list
     */
    public short getUcid() {
        return ucid;
    }

    /**
     * @return list of skinIds of allowed mods
     */
    public List<String> getSkinId() {
        return skinId.stream().map(ModSkinId::getStringValue).toList();
    }

    /**
     * Creates builder for packet request for {@link MalPacket}.
     * @param inSimConnection InSim connection to request packet from
     * @return packet request builder
     */
    public static SingleTinyPacketRequestBuilder<MalPacket> request(InSimConnection inSimConnection) {
        return new SingleTinyPacketRequestBuilder<>(inSimConnection, TinySubtype.MAL);
    }
}
