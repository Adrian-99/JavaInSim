/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.api.common.flags.Flags;
import com.github.adrian99.javainsim.internal.common.util.PacketDataBytes;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Array;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Byte;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Char;
import com.github.adrian99.javainsim.internal.insim.packets.base.InfoPacket;
import com.github.adrian99.javainsim.internal.insim.packets.util.Constants;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.insim.packets.flags.ButtonInstFlag;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;

/**
 * BuTton Type. This packet is sent by LFS when user types into a text entry button.
 */
public class BttPacket extends AbstractPacket implements InfoPacket {
    @Byte
    private final short ucid;
    @Byte(maxValue = Constants.BUTTON_MAX_CLICK_ID)
    private final short clickID;
    @Byte
    private final Flags<ButtonInstFlag> inst;
    @Byte
    private final short typeIn;
    @Char
    @Array(length = 96)
    private final String text;

    /**
     * Creates button type packet. Constructor used only internally.
     * @param reqI as received in the {@link BtnPacket}
     * @param packetDataBytes packet data bytes
     */
    public BttPacket(short reqI, PacketDataBytes packetDataBytes) {
        super(104, PacketType.BTT, reqI);
        ucid = packetDataBytes.readByte();
        clickID = packetDataBytes.readByte();
        inst = new Flags<>(ButtonInstFlag.class, packetDataBytes.readByte());
        typeIn = packetDataBytes.readByte();
        packetDataBytes.skipZeroByte();
        text = packetDataBytes.readCharArray(96);
    }

    /**
     * @return connection that typed into the button (zero if local)
     */
    public short getUcid() {
        return ucid;
    }

    /**
     * @return button identifier originally sent in {@link BtnPacket}
     */
    public short getClickID() {
        return clickID;
    }

    /**
     * @return used internally by InSim
     */
    public Flags<ButtonInstFlag> getInst() {
        return inst;
    }

    /**
     * @return whether dialog was initialised with the button's text
     */
    public boolean isInitializeWithButtonText() {
        return (typeIn & 128) == 128;
    }

    /**
     * @return max chars to type in
     */
    public short getTypeInCharacters() {
        return (short) (typeIn & 127);
    }

    /**
     * @return typed text
     */
    public String getText() {
        return text;
    }
}
