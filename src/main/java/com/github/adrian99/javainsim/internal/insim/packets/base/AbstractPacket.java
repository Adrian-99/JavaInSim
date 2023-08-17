/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.internal.insim.packets.base;

import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Byte;

/**
 * This class is a foundation, on which all packet classes are built.
 * It contains packet header fields, which are included in all packets.
 */
public abstract class AbstractPacket implements Packet {
    /**
     * Total packet size - a multiple of 4
     */
    @Byte
    protected final short size;
    /**
     * Packet identifier
     */
    @Byte
    protected final PacketType type;
    /**
     * Non-zero if the packet is a packet request or a reply to a request
     */
    @Byte
    protected short reqI;

    /**
     * Initializes packet header fields
     * @param size total packet size - a multiple of 4
     * @param type packet identifier
     * @param reqI non-zero if the packet is a packet request or a reply to a request
     */
    protected AbstractPacket(int size, PacketType type, int reqI) {
        this.size = (short) size;
        this.type = type;
        this.reqI = (short) reqI;
    }

    /**
     * @return total packet size - a multiple of 4
     */
    public short getSize() {
        return size;
    }

    /**
     * @return packet identifier
     */
    public PacketType getType() {
        return type;
    }

    /**
     * @return non-zero if the packet is a packet request or a reply to a request
     */
    public short getReqI() {
        return reqI;
    }
}
