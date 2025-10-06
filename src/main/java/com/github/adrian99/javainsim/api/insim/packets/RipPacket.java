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
import com.github.adrian99.javainsim.api.insim.packets.enums.RipErrorCode;
import com.github.adrian99.javainsim.api.insim.packets.flags.RipOption;
import com.github.adrian99.javainsim.api.insim.packets.requests.RipPacketRequest;
import com.github.adrian99.javainsim.api.insim.packets.subtypes.tiny.TinySubtypes;
import com.github.adrian99.javainsim.internal.common.util.PacketDataBytes;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Array;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Byte;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Char;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Unsigned;
import com.github.adrian99.javainsim.internal.insim.packets.base.InstructionPacket;
import com.github.adrian99.javainsim.internal.insim.packets.exceptions.PacketValidationException;
import com.github.adrian99.javainsim.internal.insim.packets.requests.builders.RipPacketRequestBuilder;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketBuilder;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketValidator;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;
import com.github.adrian99.javainsim.internal.insim.packets.base.RequestablePacket;

/**
 *  Replay Information Packet. You can load a replay or set the position in a replay
 *  with this packet. Replay positions and lengths are specified in hundredths of a second.
 *  LFS will reply with another {@link RipPacket} when the request is completed.
 */
public class RipPacket extends AbstractPacket implements InstructionPacket, RequestablePacket {
    @Byte
    private final RipErrorCode error;
    @Byte
    private final boolean isMpr;
    @Byte
    private final boolean isPaused;
    @Byte
    private final Flags<RipOption> options;
    @Unsigned
    private final long cTime;
    @Unsigned
    private final long tTime;
    @Char
    @Array(length = 64)
    private final String rName;

    /**
     * Creates replay information packet. To receive confirmation from LFS upon sending this packet that
     * request has been completed, it is recommended to use request mechanism: {@link #request} method
     * and then {@link RipPacketRequestBuilder#asConfirmationFor} method.
     * @param reqI should not be zero (however this value will be overwritten if used within {@link RipPacketRequest})
     * @param isMpr whether is MPR or SPR
     * @param isPaused whether replay should be paused
     * @param options options
     * @param cTime destination
     * @param rName name of replay to load (if empty, current replay will be used)
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public RipPacket(int reqI,
                     boolean isMpr,
                     boolean isPaused,
                     Flags<RipOption> options,
                     long cTime,
                     String rName) throws PacketValidationException {
        super(80, PacketType.RIP, reqI);
        this.error = RipErrorCode.OK;
        this.isMpr = isMpr;
        this.isPaused = isPaused;
        this.options = options;
        this.cTime = cTime;
        this.tTime = 0;
        this.rName = rName;
        PacketValidator.validate(this);
    }

    /**
     * Creates replay information packet. Constructor used only internally.
     * @param reqI 0 unless this is a reply to {@link TinySubtypes#RIP Tiny RIP}
     *             or {@link RipPacket} request
     * @param packetDataBytes packet data bytes
     */
    public RipPacket(short reqI, PacketDataBytes packetDataBytes) {
        super(80, PacketType.RIP, reqI);
        error = RipErrorCode.fromOrdinal(packetDataBytes.readByte());
        isMpr = packetDataBytes.readByte() != 0;
        isPaused = packetDataBytes.readByte() != 0;
        options = new Flags<>(RipOption.class, packetDataBytes.readByte());
        packetDataBytes.skipZeroByte();
        cTime = packetDataBytes.readUnsigned();
        tTime = packetDataBytes.readUnsigned();
        rName = packetDataBytes.readCharArray(64);
    }

    /**
     * Sets the reqI value. Method used only internally.
     * @param reqI new reqI value
     */
    public void setReqI(short reqI) {
        this.reqI = reqI;
    }

    /**
     * @return error code
     */
    public RipErrorCode getError() {
        return error;
    }

    /**
     * @return whether is MPR or SPR
     */
    public boolean isMpr() {
        return isMpr;
    }

    /**
     * @return whether replay is paused
     */
    public boolean isPaused() {
        return isPaused;
    }

    /**
     * @return options
     */
    public Flags<RipOption> getOptions() {
        return options;
    }

    /**
     * @return replay position
     */
    public long getCTime() {
        return cTime;
    }

    /**
     * @return replay length
     */
    public long getTTime() {
        return tTime;
    }

    /**
     * @return replay name, or empty if no replay is loaded
     */
    public String getRName() {
        return rName;
    }

    @Override
    public byte[] getBytes() {
        return new PacketBuilder(size, type, reqI)
                .writeByte(error.ordinal())
                .writeByte(isMpr)
                .writeByte(isPaused)
                .writeByte(options.getByteValue())
                .writeZeroByte()
                .writeUnsigned(cTime)
                .writeUnsigned(tTime)
                .writeCharArray(rName, 64)
                .getBytes();
    }

    /**
     * Creates builder for packet request for {@link RipPacket}.
     * @param inSimConnection InSim connection to request packet from
     * @return packet request builder
     */
    public static RipPacketRequestBuilder request(InSimConnection inSimConnection) {
        return new RipPacketRequestBuilder(inSimConnection);
    }
}
