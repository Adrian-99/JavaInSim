/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.api.insim.packets.enums.CscAction;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.insim.packets.enums.SmallSubtype;
import com.github.adrian99.javainsim.api.insim.packets.structures.CarContOBJ;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Byte;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Structure;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Unsigned;
import com.github.adrian99.javainsim.internal.insim.packets.base.InfoPacket;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;
import com.github.adrian99.javainsim.internal.common.util.PacketDataBytes;

/**
 * Car State Changed. The packet is sent when car of any player changes state (currently start or stop).
 */
public class CscPacket extends AbstractPacket implements InfoPacket {
    @Byte
    private final short plid;
    @Byte
    private final CscAction cscAction;
    @Unsigned
    private final long time;
    @Structure
    private final CarContOBJ c;

    /**
     * Creates car state change packet. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public CscPacket(PacketDataBytes packetDataBytes) {
        super(20, PacketType.CSC, 0);
        plid = packetDataBytes.readByte();
        packetDataBytes.skipZeroByte();
        cscAction = CscAction.fromOrdinal(packetDataBytes.readByte());
        packetDataBytes.skipZeroBytes(2);
        time = packetDataBytes.readUnsigned();
        c = new CarContOBJ(packetDataBytes);
    }

    /**
     * @return player's unique id
     */
    public short getPlid() {
        return plid;
    }

    /**
     * @return reported action
     */
    public CscAction getCscAction() {
        return cscAction;
    }

    /**
     * @return hundredths of a second since start (as in {@link SmallSubtype#RTP Small RTP})
     */
    public long getTime() {
        return time;
    }

    /**
     * @return car information
     */
    public CarContOBJ getC() {
        return c;
    }
}
