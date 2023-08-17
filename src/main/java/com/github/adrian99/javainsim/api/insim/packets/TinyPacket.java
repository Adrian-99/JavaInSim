/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.api.insim.packets.enums.TinySubtype;
import com.github.adrian99.javainsim.internal.common.util.PacketDataBytes;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Byte;
import com.github.adrian99.javainsim.internal.insim.packets.base.InfoPacket;
import com.github.adrian99.javainsim.internal.insim.packets.base.InstructionPacket;
import com.github.adrian99.javainsim.internal.insim.packets.exceptions.PacketValidationException;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketBuilder;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketValidator;

/**
 * General purpose 4 byte packet.
 */
public class TinyPacket extends AbstractPacket implements InstructionPacket, InfoPacket {
    @Byte
    private final TinySubtype subT;

    /**
     * Creates tiny packet.
     * @param reqI 0 unless it is an info request or a reply to an info request
     * @param subT subtype
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public TinyPacket(int reqI, TinySubtype subT) throws PacketValidationException {
        super(4, PacketType.TINY, reqI);
        this.subT = subT;
        PacketValidator.validate(this);
    }

    /**
     * Creates tiny packet. Constructor used only internally.
     * @param reqI 0 unless it is an info request or a reply to an info request
     * @param packetDataBytes packet data bytes
     */
    public TinyPacket(short reqI, PacketDataBytes packetDataBytes) throws PacketValidationException {
        super(4, PacketType.TINY, reqI);
        subT = TinySubtype.fromOrdinal(packetDataBytes.readByte());
    }

    @Override
    public byte[] getBytes() {
        return new PacketBuilder(size, type, reqI)
                .writeByte(subT.getByteValue())
                .getBytes();
    }

    /**
     * @return subtype
     */
    public TinySubtype getSubT() {
        return subT;
    }
}
