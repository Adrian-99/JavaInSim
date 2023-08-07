/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.insim.packets;

import pl.adrian.api.insim.InSimConnection;
import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.api.insim.packets.enums.SshErrorCode;
import pl.adrian.api.insim.packets.requests.SshPacketRequest;
import pl.adrian.internal.common.util.PacketDataBytes;
import pl.adrian.internal.insim.packets.annotations.Array;
import pl.adrian.internal.insim.packets.annotations.Byte;
import pl.adrian.internal.insim.packets.annotations.Char;
import pl.adrian.internal.insim.packets.base.AbstractPacket;
import pl.adrian.internal.insim.packets.base.InstructionPacket;
import pl.adrian.internal.insim.packets.base.RequestablePacket;
import pl.adrian.internal.insim.packets.exceptions.PacketValidationException;
import pl.adrian.internal.insim.packets.requests.builders.SshPacketRequestBuilder;
import pl.adrian.internal.insim.packets.util.PacketBuilder;
import pl.adrian.internal.insim.packets.util.PacketValidator;

/**
 * ScreenSHot. You can instruct LFS to save a screenshot in data\shots using this packet.
 * It will be saved as bmp / jpg / png as set in Misc Options.
 * Name can be a filename (excluding extension) or zero - LFS will create a name.
 * LFS will reply with another {@link SshPacket} when the request is completed.
 */
public class SshPacket extends AbstractPacket implements InstructionPacket, RequestablePacket {
    @Byte
    private final SshErrorCode error;
    @Char
    @Array(length = 32)
    private final String name;

    /**
     * Creates screenshot packet. To receive confirmation from LFS upon sending this packet that
     * request has been completed, it is recommended to use request mechanism: {@link #request} method.
     * @param reqI should not be zero (however this value will be overwritten if used within {@link SshPacketRequest})
     * @param name name of screenshot file (excluding extension) - if empty, LFS will create a name
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public SshPacket(int reqI, String name) throws PacketValidationException {
        super(40, PacketType.SSH, reqI);
        this.error = SshErrorCode.OK;
        this.name = name;
        PacketValidator.validate(this);
    }

    /**
     * Creates shreenshot packet. Constructor used only internally.
     * @param reqI 0 unless this is a reply to {@link SshPacket} request
     * @param packetDataBytes packet data bytes
     */
    public SshPacket(short reqI, PacketDataBytes packetDataBytes) {
        super(40, PacketType.SSH, reqI);
        error = SshErrorCode.fromOrdinal(packetDataBytes.readByte());
        packetDataBytes.skipZeroBytes(4);
        name = packetDataBytes.readCharArray(32);
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
    public SshErrorCode getError() {
        return error;
    }

    /**
     * @return name of screenshot file
     */
    public String getName() {
        return name;
    }

    @Override
    public byte[] getBytes() {
        return new PacketBuilder(size, type, reqI)
                .writeByte(error.ordinal())
                .writeZeroBytes(4)
                .writeCharArray(name, 32)
                .getBytes();
    }

    /**
     * Creates builder for packet request for {@link SshPacket}.
     * @param inSimConnection InSim connection to request packet from
     * @param requestPacket packet that serves as a request
     * @return packet request builder
     */
    public static SshPacketRequestBuilder request(InSimConnection inSimConnection, SshPacket requestPacket) {
        return new SshPacketRequestBuilder(inSimConnection, requestPacket);
    }
}
