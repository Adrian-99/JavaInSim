/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.insim.packets.enums.PitLaneFact;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Byte;
import com.github.adrian99.javainsim.internal.insim.packets.base.InfoPacket;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;
import com.github.adrian99.javainsim.internal.common.util.PacketDataBytes;

/**
 * Pit LAne. The packet is sent by LFS when any player enters or leaves pit lane.
 */
public class PlaPacket extends AbstractPacket implements InfoPacket {
    @Byte
    private final short plid;
    @Byte
    private final PitLaneFact fact;

    /**
     * Creates pit lane packet. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public PlaPacket(PacketDataBytes packetDataBytes) {
        super(8, PacketType.PLA, 0);
        plid = packetDataBytes.readByte();
        fact = PitLaneFact.fromOrdinal(packetDataBytes.readByte());
        packetDataBytes.skipZeroBytes(3);
    }

    /**
     * @return player's unique id
     */
    public short getPlid() {
        return plid;
    }

    /**
     * @return pit lane fact
     */
    public PitLaneFact getFact() {
        return fact;
    }
}
