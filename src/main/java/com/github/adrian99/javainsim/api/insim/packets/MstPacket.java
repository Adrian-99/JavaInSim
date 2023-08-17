/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.internal.insim.packets.base.InstructionPacket;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Array;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Char;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.internal.insim.packets.exceptions.PacketValidationException;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketBuilder;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketValidator;

/**
 * MSg Type - send to LFS to type message or command.
 */
public class MstPacket extends AbstractPacket implements InstructionPacket {
    @Char
    @Array(length = 64)
    private final String msg;

    /**
     * Creates MSg Type packet.
     * @param msg message to sent (max 63 characters)
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public MstPacket(String msg) throws PacketValidationException {
        super(68, PacketType.MST, 0);
        this.msg = msg;
        PacketValidator.validate(this);
    }

    @Override
    public byte[] getBytes() {
        return new PacketBuilder(size, type, reqI)
                .writeZeroByte()
                .writeCharArray(msg, 64)
                .getBytes();
    }
}
