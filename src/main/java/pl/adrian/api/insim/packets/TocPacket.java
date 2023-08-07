/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.insim.packets;

import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.internal.insim.packets.annotations.Byte;
import pl.adrian.internal.insim.packets.base.InfoPacket;
import pl.adrian.internal.insim.packets.base.AbstractPacket;
import pl.adrian.internal.common.util.PacketDataBytes;

/**
 * Take Over Car. The packet is sent by LFS when car take over happens.
 */
public class TocPacket extends AbstractPacket implements InfoPacket {
    @Byte
    private final short plid;
    @Byte
    private final short oldUcid;
    @Byte
    private final short newUcid;

    /**
     * Creates take over car packet. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public TocPacket(PacketDataBytes packetDataBytes) {
        super(8, PacketType.TOC, 0);
        plid = packetDataBytes.readByte();
        oldUcid = packetDataBytes.readByte();
        newUcid = packetDataBytes.readByte();
        packetDataBytes.skipZeroBytes(2);
    }

    /**
     * @return player's unique id
     */
    public short getPlid() {
        return plid;
    }

    /**
     * @return old connection's unique id
     */
    public short getOldUcid() {
        return oldUcid;
    }

    /**
     * @return new connection's unique id
     */
    public short getNewUcid() {
        return newUcid;
    }
}
