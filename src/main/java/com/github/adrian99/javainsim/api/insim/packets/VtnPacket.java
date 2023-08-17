/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.internal.common.util.PacketDataBytes;
import com.github.adrian99.javainsim.internal.insim.packets.base.InfoPacket;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.insim.packets.enums.VoteAction;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Byte;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;

/**
 * VoTe Notification. LFS notifies the external program of any votes to restart or qualify.
 */
public class VtnPacket extends AbstractPacket implements InfoPacket {
    @Byte
    private final short ucid;
    @Byte
    private final VoteAction action;

    /**
     * Creates vote notification packet. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public VtnPacket(PacketDataBytes packetDataBytes) {
        super(8, PacketType.VTN, 0);
        packetDataBytes.skipZeroByte();
        ucid = packetDataBytes.readByte();
        action = VoteAction.fromOrdinal(packetDataBytes.readByte());
        packetDataBytes.skipZeroBytes(2);
    }

    /**
     * @return connection's unique id
     */
    public short getUcid() {
        return ucid;
    }

    /**
     * @return vote action
     */
    public VoteAction getAction() {
        return action;
    }
}
