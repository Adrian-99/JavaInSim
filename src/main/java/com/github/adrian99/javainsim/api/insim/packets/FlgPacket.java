/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.api.insim.packets.enums.FlagType;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Byte;
import com.github.adrian99.javainsim.internal.insim.packets.base.InfoPacket;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;
import com.github.adrian99.javainsim.internal.common.util.PacketDataBytes;

/**
 * FLaG. The packet is sent by LFS when yellow or blue flag changes.
 */
public class FlgPacket extends AbstractPacket implements InfoPacket {
    @Byte
    private final short plid;
    @Byte
    private final boolean isOn;
    @Byte
    private final FlagType flag;
    @Byte
    private final short carBehind;

    /**
     * Creates flag packet. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public FlgPacket(PacketDataBytes packetDataBytes) {
        super(8, PacketType.FLG, 0);
        plid = packetDataBytes.readByte();
        isOn = packetDataBytes.readByte() != 0;
        flag = FlagType.fromOrdinal(packetDataBytes.readByte());
        carBehind = packetDataBytes.readByte();
        packetDataBytes.skipZeroByte();
    }

    /**
     * @return player's unique id
     */
    public short getPlid() {
        return plid;
    }

    /**
     * @return whether flag is on
     */
    public boolean isOn() {
        return isOn;
    }

    /**
     * @return flag type
     */
    public FlagType getFlag() {
        return flag;
    }

    /**
     * @return unique id of obstructed player
     */
    public short getCarBehind() {
        return carBehind;
    }
}
