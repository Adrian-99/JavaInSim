/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Int;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;
import com.github.adrian99.javainsim.internal.insim.packets.base.InstructionPacket;
import com.github.adrian99.javainsim.internal.insim.packets.exceptions.PacketValidationException;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketBuilder;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketValidator;

/**
 * MODe: send to LFS to change screen mode.
 */
public class ModPacket extends AbstractPacket implements InstructionPacket {
    @Int
    private final int bits16;
    @Int
    private final int rr;
    @Int
    private final int width;
    @Int
    private final int height;

    /**
     * Creates screen mode packet. The refresh rate actually selected by LFS will
     * be the highest available rate that is less than or equal to the specified
     * refresh rate.  Refresh rate can be specified as zero in which case the
     * default refresh rate will be used. If Width and Height are both zero,
     * LFS will switch to windowed mode.
     * @param bits16 set to choose 16-bit
     * @param rr refresh rate - zero for default
     * @param width screen width - 0 means go to window
     * @param height screen height - 0 means go to window
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public ModPacket(int bits16, int rr, int width, int height) throws PacketValidationException {
        super(20, PacketType.MOD, 0);
        this.bits16 = bits16;
        this.rr = rr;
        this.width = width;
        this.height = height;
        PacketValidator.validate(this);
    }

    @Override
    public byte[] getBytes() {
        return new PacketBuilder(size, type, reqI)
                .writeZeroByte()
                .writeInt(bits16)
                .writeInt(rr)
                .writeInt(width)
                .writeInt(height)
                .getBytes();

    }
}
