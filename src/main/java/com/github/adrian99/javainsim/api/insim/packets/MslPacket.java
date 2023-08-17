/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.internal.insim.packets.annotations.Array;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Byte;
import com.github.adrian99.javainsim.internal.insim.packets.base.InstructionPacket;
import com.github.adrian99.javainsim.api.insim.packets.enums.MessageSound;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Char;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;
import com.github.adrian99.javainsim.internal.insim.packets.exceptions.PacketValidationException;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketBuilder;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketValidator;

/**
 * MSg Local - message to appear on local computer only.
 */
public class MslPacket extends AbstractPacket implements InstructionPacket {
    @Byte
    private final MessageSound sound;
    @Char
    @Array(length = 128)
    private final String msg;

    /**
     * Creates msg local packet.
     * @param sound sound effect
     * @param msg message to send (max 127 characters)
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public MslPacket(MessageSound sound, String msg) throws PacketValidationException {
        super(132, PacketType.MSL, 0);
        this.sound = sound;
        this.msg = msg;
        PacketValidator.validate(this);
    }

    @Override
    public byte[] getBytes() {
        return new PacketBuilder(size, type, reqI)
                .writeByte(sound.ordinal())
                .writeCharArray(msg, 128)
                .getBytes();
    }
}
