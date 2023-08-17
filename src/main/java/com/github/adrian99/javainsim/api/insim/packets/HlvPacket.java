/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.api.insim.packets.enums.TinySubtype;
import com.github.adrian99.javainsim.api.insim.packets.enums.Hlvc;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.insim.packets.flags.IsiFlag;
import com.github.adrian99.javainsim.api.insim.packets.structures.CarContOBJ;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Byte;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Structure;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Word;
import com.github.adrian99.javainsim.internal.insim.packets.base.InfoPacket;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;
import com.github.adrian99.javainsim.internal.common.util.PacketDataBytes;

/**
 * Hot Lap Validity - reports of incidents that would violate HLVC.
 * To receive this packet set the {@link IsiFlag#HLV Isi HLV} flag in the {@link IsiPacket}.
 */
public class HlvPacket extends AbstractPacket implements InfoPacket {
    @Byte
    private final short plid;
    @Byte
    private final Hlvc hlvc;
    @Word
    private final int time;
    @Structure
    private final CarContOBJ c;

    /**
     * Creates hot lap validity packet.
     * @param packetDataBytes packet data bytes
     */
    public HlvPacket(PacketDataBytes packetDataBytes) {
        super(16, PacketType.HLV, 0);
        plid = packetDataBytes.readByte();
        hlvc = Hlvc.fromOrdinal(packetDataBytes.readByte());
        packetDataBytes.skipZeroByte();
        time = packetDataBytes.readWord();
        c = new CarContOBJ(packetDataBytes);
    }

    /**
     * @return player's unique id
     */
    public short getPlid() {
        return plid;
    }

    /**
     * @return HLVC
     */
    public Hlvc getHlvc() {
        return hlvc;
    }

    /**
     * @return looping time stamp (hundredths - time since reset - like {@link TinySubtype#GTH Tiny GTH})
     */
    public int getTime() {
        return time;
    }

    /**
     * @return car in contact with object information
     */
    public CarContOBJ getC() {
        return c;
    }
}
