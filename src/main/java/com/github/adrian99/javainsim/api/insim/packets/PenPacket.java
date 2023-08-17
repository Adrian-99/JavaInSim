/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.insim.packets.enums.PenaltyReason;
import com.github.adrian99.javainsim.api.insim.packets.enums.PenaltyValue;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Byte;
import com.github.adrian99.javainsim.internal.insim.packets.base.InfoPacket;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;
import com.github.adrian99.javainsim.internal.common.util.PacketDataBytes;

/**
 * PENalty. The packet is sent by LFS when any player is given or has cleared penalty.
 */
public class PenPacket extends AbstractPacket implements InfoPacket {
    @Byte
    private final short plid;
    @Byte
    private final PenaltyValue oldPen;
    @Byte
    private final PenaltyValue newPen;
    @Byte
    private final PenaltyReason reason;

    /**
     * Creates penalty packet. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public PenPacket(PacketDataBytes packetDataBytes) {
        super(8, PacketType.PEN, 0);
        plid = packetDataBytes.readByte();
        oldPen = PenaltyValue.fromOrdinal(packetDataBytes.readByte());
        newPen = PenaltyValue.fromOrdinal(packetDataBytes.readByte());
        reason = PenaltyReason.fromOrdinal(packetDataBytes.readByte());
        packetDataBytes.skipZeroByte();
    }

    /**
     * @return player's unique id
     */
    public short getPlid() {
        return plid;
    }

    /**
     * @return old penalty value
     */
    public PenaltyValue getOldPen() {
        return oldPen;
    }

    /**
     * @return new penalty value
     */
    public PenaltyValue getNewPen() {
        return newPen;
    }

    /**
     * @return penalty reason
     */
    public PenaltyReason getReason() {
        return reason;
    }
}
