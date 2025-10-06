/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.api.insim.packets.subtypes.small.SmallSubtypes;
import com.github.adrian99.javainsim.internal.common.util.PacketDataBytes;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Byte;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Structure;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Unsigned;
import com.github.adrian99.javainsim.internal.insim.packets.base.InfoPacket;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.insim.packets.enums.UcoAction;
import com.github.adrian99.javainsim.api.insim.packets.structures.CarContOBJ;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;
import com.github.adrian99.javainsim.api.insim.packets.structures.objectinfo.ObjectInfo;

/**
 * User Control Object. The packet is sent when any player crosses InSim checkpoint
 * or enters InSim circle (from layout).
 */
public class UcoPacket extends AbstractPacket implements InfoPacket {
    @Byte
    private final short plid;
    @Byte
    private final UcoAction ucoAction;
    @Unsigned
    private final long time;
    @Structure
    private final CarContOBJ c;
    @Structure
    private final ObjectInfo info;

    /**
     * Creates user control object packet. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public UcoPacket(PacketDataBytes packetDataBytes) {
        super(28, PacketType.UCO, 0);
        plid = packetDataBytes.readByte();
        packetDataBytes.skipZeroByte();
        ucoAction = UcoAction.fromOrdinal(packetDataBytes.readByte());
        packetDataBytes.skipZeroBytes(2);
        time = packetDataBytes.readUnsigned();
        c = new CarContOBJ(packetDataBytes);
        info = ObjectInfo.read(packetDataBytes);
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
    public UcoAction getUcoAction() {
        return ucoAction;
    }

    /**
     * @return hundredths of a second since start (as in {@link SmallSubtypes#RTP Small RTP})
     */
    public long getTime() {
        return time;
    }

    /**
     * @return car in contact with object information
     */
    public CarContOBJ getC() {
        return c;
    }

    /**
     * @return info about the checkpoint or circle
     */
    public ObjectInfo getInfo() {
        return info;
    }
}
