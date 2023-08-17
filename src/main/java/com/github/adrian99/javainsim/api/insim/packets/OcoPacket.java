/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.internal.insim.packets.base.InstructionPacket;
import com.github.adrian99.javainsim.api.insim.packets.enums.OcoAction;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.insim.packets.enums.StartLightsIndex;
import com.github.adrian99.javainsim.api.common.flags.Flags;
import com.github.adrian99.javainsim.api.insim.packets.flags.StartLightsDataFlag;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Byte;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;
import com.github.adrian99.javainsim.internal.insim.packets.exceptions.PacketValidationException;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketBuilder;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketValidator;

/**
 * Object COntrol. The packet is used for switching start lights.
 */
public class OcoPacket extends AbstractPacket implements InstructionPacket {
    @Byte
    private final OcoAction ocoAction;
    @Byte
    private final StartLightsIndex index;
    @Byte
    private final short identifier;
    @Byte
    private final Flags<StartLightsDataFlag> data;

    /**
     * Creates object control packet.
     * @param ocoAction requested action
     * @param index specifies which lights should be overridden
     * @param identifier identify particular start lights objects (0 to 63 or 255 = all)
     * @param data specifies particular bulbs
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public OcoPacket(OcoAction ocoAction,
                     StartLightsIndex index,
                     int identifier,
                     Flags<StartLightsDataFlag> data) throws PacketValidationException {
        super(8, PacketType.OCO, 0);
        this.ocoAction = ocoAction;
        this.index = index;
        this.identifier = (short) identifier;
        this.data = data;
        PacketValidator.validate(this);
    }

    @Override
    public byte[] getBytes() {
        return new PacketBuilder(size, type, reqI)
                .writeZeroByte()
                .writeByte(ocoAction.getValue())
                .writeByte(index.getValue())
                .writeByte(identifier)
                .writeByte(data.getByteValue())
                .getBytes();
    }
}
