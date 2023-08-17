/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.common.flags.Flags;
import com.github.adrian99.javainsim.api.insim.packets.flags.PlayerFlag;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Byte;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Word;
import com.github.adrian99.javainsim.internal.insim.packets.base.InfoPacket;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;
import com.github.adrian99.javainsim.internal.common.util.PacketDataBytes;

/**
 * Player FLags. The packet is sent by LFS when flags of any player change.
 */
public class PflPacket extends AbstractPacket implements InfoPacket {
    @Byte
    private final short plid;
    @Word
    private final Flags<PlayerFlag> flags;

    /**
     * Creates player flags packet. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public PflPacket(PacketDataBytes packetDataBytes) {
        super(8, PacketType.PFL, 0);
        plid = packetDataBytes.readByte();
        flags = new Flags<>(PlayerFlag.class, packetDataBytes.readWord());
        packetDataBytes.skipZeroBytes(2);
    }

    /**
     * @return player's unique id
     */
    public short getPlid() {
        return plid;
    }

    /**
     * @return player flags
     */
    public Flags<PlayerFlag> getFlags() {
        return flags;
    }
}
