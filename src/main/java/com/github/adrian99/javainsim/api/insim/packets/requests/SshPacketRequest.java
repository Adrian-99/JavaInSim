/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.requests;

import org.slf4j.LoggerFactory;
import com.github.adrian99.javainsim.api.insim.PacketListener;
import com.github.adrian99.javainsim.api.insim.packets.SshPacket;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.internal.insim.packets.base.InstructionPacket;
import com.github.adrian99.javainsim.internal.insim.packets.requests.AbstractPacketRequest;

/**
 * Packet request where {@link SshPacket} serves as a request packet. Only {@link SshPacket}
 * can be requested this way.
 */
public class SshPacketRequest extends AbstractPacketRequest<SshPacket> {
    private final SshPacket requestPacket;

    /**
     * Creates packet request.
     * @param requestPacket packet that serves as a request
     * @param callback method to be called when requested packet is received
     * @param timeoutMillis period of time (in milliseconds) after which packet request should be considered timed out
     */
    public SshPacketRequest(SshPacket requestPacket, PacketListener<SshPacket> callback, long timeoutMillis) {
        super(LoggerFactory.getLogger(SshPacketRequest.class), PacketType.SSH, true, callback, timeoutMillis);
        this.requestPacket = requestPacket;
    }

    @Override
    protected InstructionPacket createRequestPacket(short reqI) {
        requestPacket.setReqI(reqI);
        return requestPacket;
    }
}
