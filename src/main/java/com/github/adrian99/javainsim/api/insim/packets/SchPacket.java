/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.internal.insim.packets.base.InstructionPacket;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.common.flags.Flags;
import com.github.adrian99.javainsim.api.insim.packets.flags.SchFlag;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Byte;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;
import com.github.adrian99.javainsim.internal.insim.packets.exceptions.PacketValidationException;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketBuilder;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketValidator;

/**
 * Single CHaracter. Allows sending individual key presses to LFS.
 * For standard keys (e.g. V and H) a capital letter should be sent.
 * This does not work with some keys like F keys, arrows or CTRL keys.
 * Use {@link MstPacket} with the /press /shift /ctrl /alt commands for this purpose.
 */
public class SchPacket extends AbstractPacket implements InstructionPacket {
    @Byte
    private final char charB;
    @Byte
    private final Flags<SchFlag> flags;

    /**
     * Creates single character packet.
     * @param charB key to press
     * @param flags key flags
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public SchPacket(char charB, Flags<SchFlag> flags) throws PacketValidationException {
        super(8, PacketType.SCH, 0);
        this.charB = charB;
        this.flags = flags;
        PacketValidator.validate(this);
    }
    @Override
    public byte[] getBytes() {
        return new PacketBuilder(size, type, reqI)
                .writeZeroByte()
                .writeByte(charB)
                .writeByte(flags.getByteValue())
                .writeZeroBytes(2)
                .getBytes();
    }
}
