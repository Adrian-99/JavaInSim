/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.internal.insim.packets.annotations.Byte;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Word;
import com.github.adrian99.javainsim.internal.insim.packets.base.InstructionPacket;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.insim.packets.enums.SfpFlag;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;
import com.github.adrian99.javainsim.internal.insim.packets.exceptions.PacketValidationException;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketBuilder;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketValidator;

/**
 * State Flags Pack.
 */
public class SfpPacket extends AbstractPacket implements InstructionPacket {
    @Word
    private final SfpFlag flag;
    @Byte
    private final boolean on;

    /**
     * Creates state flags pack packet.
     * @param flag the state to set
     * @param on desired value of specified state
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public SfpPacket(SfpFlag flag, boolean on) throws PacketValidationException {
        super(8, PacketType.SFP, 0);
        this.flag = flag;
        this.on = on;
        PacketValidator.validate(this);
    }

    @Override
    public byte[] getBytes() {
        return new PacketBuilder(size, type, reqI)
                .writeZeroByte()
                .writeWord(flag.getValue())
                .writeByte(on)
                .writeZeroByte()
                .getBytes();
    }
}
