/*
 * Copyright (c) 2024, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.api.common.enums.DefaultCar;
import com.github.adrian99.javainsim.api.common.flags.Flags;
import com.github.adrian99.javainsim.api.insim.InSimConnection;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.insim.packets.enums.SmallSubtype;
import com.github.adrian99.javainsim.api.insim.packets.enums.TinySubtype;
import com.github.adrian99.javainsim.api.insim.packets.enums.VoteAction;
import com.github.adrian99.javainsim.api.insim.packets.flags.LclFlag;
import com.github.adrian99.javainsim.api.insim.packets.flags.LcsFlag;
import com.github.adrian99.javainsim.internal.common.util.PacketDataBytes;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Byte;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Unsigned;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;
import com.github.adrian99.javainsim.internal.insim.packets.base.InstructionPacket;
import com.github.adrian99.javainsim.internal.insim.packets.exceptions.PacketValidationException;
import com.github.adrian99.javainsim.internal.insim.packets.requests.builders.SmallPacketRequestBuilder;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketBuilder;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketValidator;
import com.github.adrian99.javainsim.internal.insim.packets.base.RequestablePacket;

import java.util.Optional;

/**
 * General purpose 8 byte packet.
 */
public class SmallPacket extends AbstractPacket implements InstructionPacket, RequestablePacket {
    @Byte
    private final SmallSubtype subT;
    @Unsigned
    private final long uVal;

    /**
     * Creates small packet.
     * @param subT subtype
     * @param uVal value
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public SmallPacket(SmallSubtype subT, long uVal) throws PacketValidationException {
        super(8, PacketType.SMALL, 0);
        this.subT = subT;
        this.uVal = uVal;
        PacketValidator.validate(this);
    }

    /**
     * Creates small packet. Constructor used only internally.
     * @param reqI 0 unless this is a reply to an {@link TinySubtype#ALC Tiny ALC}
     *             or {@link TinySubtype#GTH Tiny GTH} request
     * @param packetDataBytes packet data bytes
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public SmallPacket(short reqI, PacketDataBytes packetDataBytes) throws PacketValidationException {
        super(8, PacketType.SMALL, reqI);
        subT = SmallSubtype.fromOrdinal(packetDataBytes.readByte());
        uVal = packetDataBytes.readUnsigned();
    }

    /**
     * Creates small packet for local car switches.
     * @param lcsFlags local car switches
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public SmallPacket(LcsFlag... lcsFlags) throws PacketValidationException {
        this(SmallSubtype.LCS, new Flags<>(lcsFlags).getUnsignedValue());
    }

    /**
     * Creates small packet for local car lights.
     * @param lclFlags local car lights
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public SmallPacket(LclFlag... lclFlags) throws PacketValidationException {
        this(SmallSubtype.LCL, new Flags<>(lclFlags).getUnsignedValue());
    }

    /**
     * Creates small packet for setting allowed cars on host.
     * @param cars allowed cars
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public SmallPacket(DefaultCar... cars) throws PacketValidationException {
        this(SmallSubtype.ALC, new Flags<>(cars).getUnsignedValue());
    }

    /**
     * Creates small packet to change rate of {@link NlpPacket} or {@link MciPacket} after initialisation.
     * @param interval 0 means stop, otherwise time interval: 40, 50, 60... 8000 ms
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public SmallPacket(long interval) throws PacketValidationException {
        this(SmallSubtype.NLI, interval);
    }

    @Override
    public byte[] getBytes() {
        return new PacketBuilder(size, type, reqI)
                .writeByte(subT.ordinal())
                .writeUnsigned(uVal)
                .getBytes();
    }

    /**
     * @return subtype
     */
    public SmallSubtype getSubT() {
        return subT;
    }

    /**
     * @return value
     */
    public long getUVal() {
        return uVal;
    }

    /**
     * @return vote action, or empty optional if {@link #getSubT() subtype} is
     * not equal to {@link SmallSubtype#VTA}
     */
    public Optional<VoteAction> getVoteAction() {
        if (subT.equals(SmallSubtype.VTA)) {
            return Optional.of(VoteAction.fromOrdinal((int) uVal));
        } else {
            return Optional.empty();
        }
    }

    /**
     * @return allowed cars, or empty optional if {@link #getSubT() subtype} is
     * not equal to {@link SmallSubtype#ALC}
     */
    public Optional<Flags<DefaultCar>> getCars() {
        if (subT.equals(SmallSubtype.ALC)) {
            return Optional.of(new Flags<>(DefaultCar.class, (int) uVal));
        } else {
            return Optional.empty();
        }
    }

    /**
     * Creates builder for packet request for {@link SmallPacket}.
     * @param inSimConnection InSim connection to request packet from
     * @return packet request builder
     */
    public static SmallPacketRequestBuilder request(InSimConnection inSimConnection) {
        return new SmallPacketRequestBuilder(inSimConnection);
    }
}
