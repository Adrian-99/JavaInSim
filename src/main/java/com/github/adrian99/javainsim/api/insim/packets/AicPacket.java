/*
 * Copyright (c) 2025, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.api.insim.packets.enums.AIInputType;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.insim.packets.structures.AIInputVal;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Array;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Byte;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Structure;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;
import com.github.adrian99.javainsim.internal.insim.packets.base.InstructionPacket;
import com.github.adrian99.javainsim.internal.insim.packets.exceptions.PacketValidationException;
import com.github.adrian99.javainsim.internal.insim.packets.util.Constants;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketBuilder;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketUtils;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketValidator;

import java.util.List;

/**
 * AI Control - variable size<br>
 * If the Time value of {@link AIInputVal} is set, that input will return to default after that time.<br>
 * This is probably most useful for {@link AIInputType#CHUP} / {@link AIInputType#CHDN} / {@link AIInputType#FLASH} / {@link AIInputType#HORN} inputs.
 * If Time is not set, another packet will need to be sent to zero the input.
 */
public class AicPacket extends AbstractPacket implements InstructionPacket {
    @Byte
    private final short plid;
    @Structure
    @Array(length = Constants.AIC_MAX_INPUTS, dynamicLength = true)
    private final List<AIInputVal> inputs;

    /**
     * Creates AI control packet.
     * @param plid unique ID of AI driver to control
     * @param inputs inputs to set
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public AicPacket(int plid, List<AIInputVal> inputs) throws PacketValidationException {
        super(
                PacketUtils.getPacketSize(4, inputs.size(), 4, Constants.AIC_MAX_INPUTS),
                PacketType.AIC,
                0
        );
        this.plid = (short) plid;
        this.inputs = inputs;
        PacketValidator.validate(this);
    }

    @Override
    public byte[] getBytes() {
        return new PacketBuilder(size, type, reqI)
                .writeByte(plid)
                .writeStructureArray(inputs, 8)
                .getBytes();
    }
}
