/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.insim.packets.subtypes.ttc.TtcSubtype;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Byte;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;
import com.github.adrian99.javainsim.internal.insim.packets.base.InstructionPacket;
import com.github.adrian99.javainsim.internal.insim.packets.exceptions.PacketValidationException;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketBuilder;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketValidator;

/**
 * General purpose 8 byte packet (Target To Connection).
 */
public class TtcPacket extends AbstractPacket implements InstructionPacket {
    @Byte
    private final TtcSubtype subT;
    @Byte
    private final short ucid;
    @Byte
    private final short b1;
    @Byte
    private final short b2;
    @Byte
    private final short b3;

    /**
     * Creates ttc packet.
     * @param subT subtype
     * @param ucid connection's unique id (0 = local)
     * @param b1 may be used in various ways depending on SubT
     * @param b2 may be used in various ways depending on SubT
     * @param b3 may be used in various ways depending on SubT
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public TtcPacket(TtcSubtype subT, int ucid, int b1, int b2, int b3) throws PacketValidationException {
        this(subT, ucid, b1, b2, b3, 0);
    }

    /**
     * Creates ttc packet.
     * @param subT subtype
     * @param ucid connection's unique id (0 = local)
     * @param b1 may be used in various ways depending on SubT
     * @param b2 may be used in various ways depending on SubT
     * @param b3 may be used in various ways depending on SubT
     * @param reqI 0 unless it is an info request or a reply to an info request
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public TtcPacket(TtcSubtype subT, int ucid, int b1, int b2, int b3, int reqI) throws PacketValidationException {
        super(8, PacketType.TTC, reqI);
        this.subT = subT;
        this.ucid = (short) ucid;
        this.b1 = (short) b1;
        this.b2 = (short) b2;
        this.b3 = (short) b3;
        PacketValidator.validate(this);
    }

    @Override
    public byte[] getBytes() {
        return new PacketBuilder(size, type, reqI)
                .writeByte(subT.getByteValue())
                .writeByte(ucid)
                .writeByte(b1)
                .writeByte(b2)
                .writeByte(b3)
                .getBytes();
    }
}
