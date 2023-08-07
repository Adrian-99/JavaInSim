/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.insim.packets;

import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.internal.insim.packets.annotations.Array;
import pl.adrian.internal.insim.packets.annotations.Byte;
import pl.adrian.internal.insim.packets.annotations.Char;
import pl.adrian.internal.insim.packets.base.AbstractPacket;
import pl.adrian.internal.insim.packets.base.InfoPacket;
import pl.adrian.internal.common.util.PacketDataBytes;

/**
 * InsIm Info - /i message from user to host's InSim - variable size.
 */
public class IiiPacket extends AbstractPacket implements InfoPacket {
    @Byte
    private final short ucid;
    @Byte
    private final short plid;
    @Char
    @Array(length = 64)
    private final String msg;

    /**
     * Creates InSim info packet. Constructor used only internally.
     * @param size packet size
     * @param packetDataBytes packet data bytes
     */
    public IiiPacket(short size, PacketDataBytes packetDataBytes) {
        super(size, PacketType.III, 0);
        packetDataBytes.skipZeroByte();
        ucid = packetDataBytes.readByte();
        plid = packetDataBytes.readByte();
        packetDataBytes.skipZeroBytes(2);
        msg = packetDataBytes.readCharArray(size - 8);
    }

    /**
     * @return connection's unique id (0 = host)
     */
    public short getUcid() {
        return ucid;
    }

    /**
     * @return player's unique id (if zero, use {@link #getUcid})
     */
    public short getPlid() {
        return plid;
    }

    /**
     * @return message
     */
    public String getMsg() {
        return msg;
    }
}
