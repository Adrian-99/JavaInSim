/*
 * Copyright (c) 2025, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.structures;

import com.github.adrian99.javainsim.api.insim.packets.AicPacket;
import com.github.adrian99.javainsim.api.insim.packets.enums.AIInputType;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Byte;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Word;
import com.github.adrian99.javainsim.internal.insim.packets.structures.base.ComplexInstructionStructure;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketBuilder;

/**
 * This class stores information about AI input. There is an array of these in the {@link AicPacket}.
 */
public class AIInputVal implements ComplexInstructionStructure {
    @Byte
    private final AIInputType input;
    @Byte
    private final short time;
    @Word
    private final int value;

    /**
     * Creates AI input without time to hold.
     * @param input input value to set
     * @param value value to set
     */
    public AIInputVal(AIInputType input, int value) {
        this(input, 0, value);
    }

    /**
     * Creates AI input with time to hold.
     * @param input input value to set
     * @param time time to hold (hundredths of a second)
     * @param value value to set
     */
    public AIInputVal(AIInputType input, int time, int value) {
        this.input = input;
        this.time = (short) time;
        this.value = value;
    }

    @Override
    public void appendBytes(PacketBuilder packetBuilder) {
        packetBuilder.writeByte(input.getValue())
                .writeByte(time)
                .writeWord(value);
    }
}
