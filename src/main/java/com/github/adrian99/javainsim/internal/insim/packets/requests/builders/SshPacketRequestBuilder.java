/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.internal.insim.packets.requests.builders;

import com.github.adrian99.javainsim.api.insim.packets.requests.SshPacketRequest;
import com.github.adrian99.javainsim.api.insim.InSimConnection;
import com.github.adrian99.javainsim.api.insim.PacketListener;
import com.github.adrian99.javainsim.api.insim.packets.SshPacket;
import com.github.adrian99.javainsim.internal.insim.packets.requests.PacketRequest;
import com.github.adrian99.javainsim.internal.insim.packets.requests.builders.base.NonRecurringPacketRequestBuilder;
import com.github.adrian99.javainsim.internal.insim.packets.requests.builders.base.SinglePacketRequestBuilder;

/**
 * Builder of packet request where {@link SshPacket} serves as a request. Only {@link SshPacket}
 * can be requested this way.
 */
public class SshPacketRequestBuilder
        extends NonRecurringPacketRequestBuilder<SshPacket>
        implements SinglePacketRequestBuilder<SshPacket> {
    private final SshPacket requestPacket;

    /**
     * Creates builder for {@link SshPacket} request.
     * @param inSimConnection InSim connection that packet should be requested from
     * @param requestPacket packet that serves as a request
     */
    public SshPacketRequestBuilder(InSimConnection inSimConnection, SshPacket requestPacket) {
        super(inSimConnection);
        this.requestPacket = requestPacket;
    }

    @Override
    protected PacketRequest buildPacketRequest(PacketListener<SshPacket> callback) {
        return new SshPacketRequest(requestPacket, callback, requestTimeoutMillis);
    }
}
