/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.api.common.flags.Flags;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.insim.packets.flags.ButtonInstFlag;
import com.github.adrian99.javainsim.api.insim.packets.flags.ClickFlag;
import com.github.adrian99.javainsim.internal.common.util.PacketDataBytes;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Byte;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;
import com.github.adrian99.javainsim.internal.insim.packets.base.InfoPacket;
import com.github.adrian99.javainsim.internal.insim.packets.util.Constants;

/**
 * BuTton Click. This packet is sent by LFS when user clicks on a clickable button.
 */
public class BtcPacket extends AbstractPacket implements InfoPacket {
    @Byte
    private final short ucid;
    @Byte(maxValue = Constants.BUTTON_MAX_CLICK_ID)
    private final short clickID;
    @Byte
    private final Flags<ButtonInstFlag> inst;
    @Byte
    private final Flags<ClickFlag> cFlags;

    /**
     * Creates button click packet. Constructor used only internally.
     * @param reqI as received in the {@link BtnPacket}
     * @param packetDataBytes packet data bytes
     */
    public BtcPacket(short reqI, PacketDataBytes packetDataBytes) {
        super(8, PacketType.BTC, reqI);
        ucid = packetDataBytes.readByte();
        clickID = packetDataBytes.readByte();
        inst = new Flags<>(ButtonInstFlag.class, packetDataBytes.readByte());
        cFlags = new Flags<>(ClickFlag.class, packetDataBytes.readByte());
        packetDataBytes.skipZeroByte();
    }

    /**
     * @return connection that clicked the button (zero if local)
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
     * @return button click flags
     */
    public Flags<ClickFlag> getCFlags() {
        return cFlags;
    }
}
