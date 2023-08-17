/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.api.common.flags.Flags;
import com.github.adrian99.javainsim.api.insim.packets.enums.BfnSubtype;
import com.github.adrian99.javainsim.internal.common.util.PacketDataBytes;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Byte;
import com.github.adrian99.javainsim.internal.insim.packets.base.InfoPacket;
import com.github.adrian99.javainsim.internal.insim.packets.base.InstructionPacket;
import com.github.adrian99.javainsim.internal.insim.packets.exceptions.PacketValidationException;
import com.github.adrian99.javainsim.internal.insim.packets.util.Constants;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.insim.packets.flags.ButtonInstFlag;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketBuilder;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketValidator;

/**
 * Button FunctioN. Send this packet to delete one button or a range of buttons or clear all buttons.
 * This packet is sent by LFS when button request is received.
 */
public class BfnPacket extends AbstractPacket implements InstructionPacket, InfoPacket {
    @Byte
    private final BfnSubtype subT;
    @Byte
    private final short ucid;
    @Byte(maxValue = Constants.BUTTON_MAX_CLICK_ID)
    private final short clickID;
    @Byte(maxValue = Constants.BUTTON_MAX_CLICK_ID)
    private final short clickMax;
    @Byte
    private final Flags<ButtonInstFlag> inst;

    /**
     * Creates button function packet.
     * @param subT subtype
     * @param ucid connection to send to (0 = local / 255 = all)
     * @param clickID if {@link #subT} is {@link BfnSubtype#DEL_BTN}: ID of single button to delete or first button in range
     * @param clickMax if {@link #subT} is {@link BfnSubtype#DEL_BTN}: ID of last button in range (if greater than {@link #clickID})
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public BfnPacket(BfnSubtype subT,
                     int ucid,
                     int clickID,
                     int clickMax) throws PacketValidationException {
        super(8, PacketType.BFN, 0);
        this.subT = subT;
        this.ucid = (short) ucid;
        this.clickID = (short) clickID;
        this.clickMax = (short) clickMax;
        this.inst = new Flags<>();
        PacketValidator.validate(this);
    }

    /**
     * Creates button function packet. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public BfnPacket(PacketDataBytes packetDataBytes) {
        super(8, PacketType.BFN, 0);
        subT = BfnSubtype.fromOrdinal(packetDataBytes.readByte());
        ucid = packetDataBytes.readByte();
        clickID = packetDataBytes.readByte();
        clickMax = packetDataBytes.readByte();
        inst = new Flags<>(ButtonInstFlag.class, packetDataBytes.readByte());
    }

    /**
     * @return subtype
     */
    public BfnSubtype getSubT() {
        return subT;
    }

    /**
     * @return connection received from (0 = local / 255 = all)
     */
    public short getUcid() {
        return ucid;
    }

    @Override
    public byte[] getBytes() {
        return new PacketBuilder(size, type, reqI)
                .writeByte(subT.ordinal())
                .writeByte(ucid)
                .writeByte(clickID)
                .writeByte(clickMax)
                .writeByte(inst.getByteValue())
                .getBytes();
    }
}
