/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.internal.common.util.PacketDataBytes;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Byte;
import com.github.adrian99.javainsim.internal.insim.packets.base.InfoPacket;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;

/**
 * AutoX Object. The packet is sent by LFS when an autocross object is hit (2 second time penalty).
 */
public class AxoPacket extends AbstractPacket implements InfoPacket {
    @Byte
    private final short plid;

    /**
     * Creates autoX object packet. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public AxoPacket(PacketDataBytes packetDataBytes) {
        super(4, PacketType.AXO, 0);
        plid = packetDataBytes.readByte();
    }

    /**
     * @return player's unique id
     */
    public short getPlid() {
        return plid;
    }
}
