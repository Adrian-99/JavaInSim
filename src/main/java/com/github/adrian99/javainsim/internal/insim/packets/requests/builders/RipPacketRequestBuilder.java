/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.internal.insim.packets.requests.builders;

import com.github.adrian99.javainsim.api.insim.InSimConnection;
import com.github.adrian99.javainsim.api.insim.PacketListener;
import com.github.adrian99.javainsim.api.insim.packets.RipPacket;
import com.github.adrian99.javainsim.api.insim.packets.enums.TinySubtype;
import com.github.adrian99.javainsim.api.insim.packets.requests.RipPacketRequest;

import java.io.IOException;

/**
 * Builder of packet request where {@link RipPacket} serves as a request. Only {@link RipPacket}
 * can be requested this way.
 */
public class RipPacketRequestBuilder extends SingleTinyPacketRequestBuilder<RipPacket> {
    private RipPacket ripPacket;

    /**
     * Creates builder for {@link RipPacket} request.
     * @param inSimConnection InSim connection that packet should be requested from
     */
    public RipPacketRequestBuilder(InSimConnection inSimConnection) {
        super(inSimConnection, TinySubtype.RIP);
        ripPacket = null;
        requestTimeoutMillis = 300000;
    }

    /**
     * Changes request timeout (default 30000 ms)
     * @param requestTimeout request timeout in milliseconds
     * @return packet builder
     */
    public RipPacketRequestBuilder setRequestTimeout(long requestTimeout) {
        requestTimeoutMillis = requestTimeout;
        return this;
    }

    /**
     * Option to choose if requested packet should be confirmation that request was completed.
     * @param ripPacket packet that serves as a request
     * @return packet request builder
     */
    public RipPacketRequestBuilder asConfirmationFor(RipPacket ripPacket) {
        this.ripPacket = ripPacket;
        return this;
    }

    @Override
    public void listen(PacketListener<RipPacket> callback) throws IOException {
        if (ripPacket != null) {
            inSimConnection.request(new RipPacketRequest(ripPacket, callback, requestTimeoutMillis));
        } else {
            super.listen(callback);
        }
    }
}
