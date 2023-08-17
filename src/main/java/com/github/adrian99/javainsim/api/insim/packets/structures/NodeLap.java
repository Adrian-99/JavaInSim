/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.structures;

import com.github.adrian99.javainsim.api.insim.packets.NlpPacket;
import com.github.adrian99.javainsim.internal.common.util.PacketDataBytes;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Byte;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Word;

/**
 * Car info in 6 bytes - there is an array of these in the {@link NlpPacket NlpPacket}.
 */
public class NodeLap {
    @Word
    private final int node;
    @Word
    private final int lap;
    @Byte
    private final short plid;
    @Byte
    private final short position;

    /**
     * Creates car information. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public NodeLap(PacketDataBytes packetDataBytes) {
        node = packetDataBytes.readWord();
        lap = packetDataBytes.readWord();
        plid = packetDataBytes.readByte();
        position = packetDataBytes.readByte();
    }

    /**
     * @return current path node
     */
    public int getNode() {
        return node;
    }

    /**
     * @return current lap
     */
    public int getLap() {
        return lap;
    }

    /**
     * @return player's unique id
     */
    public short getPlid() {
        return plid;
    }

    /**
     * @return current race position: 0 = unknown, 1 = leader, etc...
     */
    public short getPosition() {
        return position;
    }
}
