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
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Char;
import com.github.adrian99.javainsim.internal.insim.packets.base.InstructionPacket;
import com.github.adrian99.javainsim.internal.insim.packets.exceptions.PacketValidationException;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketUtils;
import com.github.adrian99.javainsim.api.insim.packets.enums.MessageSound;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketBuilder;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketValidator;

/**
 * Msg To Connection - hosts only - send to a connection / a player / all.
 */
public class MtcPacket extends AbstractPacket implements InstructionPacket {
    @Byte
    private final MessageSound sound;
    @Byte
    private final short ucid;
    @Byte
    private final short plid;
    @Char
    @Array(length = 128)
    private final String text;

    /**
     * Creates msg to connection packet.
     * @param sound sound effect
     * @param ucid connection's unique id (0 = host / 255 = all)
     * @param plid player's unique id (if zero, use ucid)
     * @param text text to send (max 127 characters)
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public MtcPacket(MessageSound sound, int ucid, int plid, String text) throws PacketValidationException {
        super(PacketUtils.getPacketSize(8, text, 128), PacketType.MTC, 0);
        this.sound = sound;
        this.ucid = (short) ucid;
        this.plid = (short) plid;
        this.text = text;
        PacketValidator.validate(this);
    }

    @Override
    public byte[] getBytes() {
        return new PacketBuilder(size, type, reqI)
                .writeByte(sound.ordinal())
                .writeByte(ucid)
                .writeByte(plid)
                .writeZeroBytes(2)
                .writeCharArray(text, 128, 4)
                .getBytes();
    }
}
