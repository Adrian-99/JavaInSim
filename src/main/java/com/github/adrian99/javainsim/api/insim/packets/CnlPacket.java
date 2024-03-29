/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.internal.common.util.PacketDataBytes;
import com.github.adrian99.javainsim.api.insim.packets.enums.LeaveReason;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Byte;
import com.github.adrian99.javainsim.internal.insim.packets.base.InfoPacket;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;

/**
 * ConN Leave. The packet is sent by LFS when connection leaves.
 */
public class CnlPacket extends AbstractPacket implements InfoPacket {
    @Byte
    private final short ucid;
    @Byte
    private final LeaveReason reason;
    @Byte
    private final short total;

    /**
     * Creates conn leave packet. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public CnlPacket(PacketDataBytes packetDataBytes) {
        super(8, PacketType.CNL, 0);
        ucid = packetDataBytes.readByte();
        reason = LeaveReason.fromOrdinal(packetDataBytes.readByte());
        total = packetDataBytes.readByte();
        packetDataBytes.skipZeroBytes(2);
    }

    /**
     * @return unique id of the connection which left
     */
    public short getUcid() {
        return ucid;
    }

    /**
     * @return leave reason
     */
    public LeaveReason getReason() {
        return reason;
    }

    /**
     * @return number of connections including host
     */
    public short getTotal() {
        return total;
    }
}
