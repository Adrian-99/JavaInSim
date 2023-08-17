/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.requests;

import com.github.adrian99.javainsim.internal.insim.packets.base.InstructionPacket;
import com.github.adrian99.javainsim.internal.insim.packets.requests.AbstractPacketRequest;
import org.slf4j.LoggerFactory;
import com.github.adrian99.javainsim.api.insim.PacketListener;
import com.github.adrian99.javainsim.api.insim.packets.RipPacket;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;

/**
 * Packet request where {@link RipPacket} serves as a request packet. Only {@link RipPacket}
 * can be requested this way.
 */
public class RipPacketRequest extends AbstractPacketRequest<RipPacket> {
    private final RipPacket requestPacket;

    /**
     * Creates packet request.
     * @param requestPacket packet that serves as a request
     * @param callback method to be called when requested packet is received
     * @param timeoutMillis period of time (in milliseconds) after which packet request should be considered timed out
     */
    public RipPacketRequest(RipPacket requestPacket, PacketListener<RipPacket> callback, long timeoutMillis) {
        super(LoggerFactory.getLogger(RipPacketRequest.class), PacketType.RIP, true, callback, timeoutMillis);
        this.requestPacket = requestPacket;
    }

    @Override
    protected InstructionPacket createRequestPacket(short reqI) {
        requestPacket.setReqI(reqI);
        return requestPacket;
    }
}
