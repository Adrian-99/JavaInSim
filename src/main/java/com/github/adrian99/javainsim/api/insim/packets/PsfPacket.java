/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Byte;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Unsigned;
import com.github.adrian99.javainsim.internal.insim.packets.base.InfoPacket;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;
import com.github.adrian99.javainsim.internal.common.util.PacketDataBytes;

/**
 * Pit Stop Finished. The packet is sent by LFS when any player finishes their pit stop.
 */
public class PsfPacket extends AbstractPacket implements InfoPacket {
    @Byte
    private final short plid;
    @Unsigned
    private final long sTime;

    /**
     * Creates pit stop finished packet. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public PsfPacket(PacketDataBytes packetDataBytes) {
        super(12, PacketType.PSF, 0);
        plid = packetDataBytes.readByte();
        sTime = packetDataBytes.readUnsigned();
        packetDataBytes.skipZeroBytes(4);
    }

    /**
     * @return player's unique id
     */
    public short getPlid() {
        return plid;
    }

    /**
     * @return stop time (ms)
     */
    public long getSTime() {
        return sTime;
    }
}
