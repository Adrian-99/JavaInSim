/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Array;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Byte;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Char;
import com.github.adrian99.javainsim.internal.insim.packets.base.InfoPacket;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;
import com.github.adrian99.javainsim.internal.common.util.PacketDataBytes;

/**
 * Conn Player Rename. The packet is sent by LFS when player renames.
 */
public class CprPacket extends AbstractPacket implements InfoPacket {
    @Byte
    private final short ucid;
    @Char
    @Array(length = 24)
    private final String pName;
    @Char
    @Array(length = 8)
    private final String plate;

    /**
     * Creates conn player rename packet. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public CprPacket(PacketDataBytes packetDataBytes) {
        super(36, PacketType.CPR, 0);
        ucid = packetDataBytes.readByte();
        pName = packetDataBytes.readCharArray(24);
        plate = packetDataBytes.readCharArray(8);
    }

    /**
     * @return unique id of the connection
     */
    public short getUcid() {
        return ucid;
    }

    /**
     * @return new name
     */
    public String getPName() {
        return pName;
    }

    /**
     * @return number plate
     */
    public String getPlate() {
        return plate;
    }
}
