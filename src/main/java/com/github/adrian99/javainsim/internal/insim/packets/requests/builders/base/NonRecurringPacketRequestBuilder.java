/*
 * Copyright (c) 2025, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.internal.insim.packets.requests.builders.base;

import com.github.adrian99.javainsim.api.insim.InSimConnection;
import com.github.adrian99.javainsim.api.insim.PacketListener;
import com.github.adrian99.javainsim.internal.insim.packets.base.RequestablePacket;
import com.github.adrian99.javainsim.internal.insim.packets.requests.PacketRequest;

import java.io.IOException;

/**
 * Basic builder of non-recurring packet request. Both single and multiple packet
 * responses are allowed.
 * @param <T> type of packet to be requested
 */
public abstract class NonRecurringPacketRequestBuilder<T extends RequestablePacket> extends BasePacketRequestBuilder {
    /**
     * Creates basic builder of packet request.
     * @param inSimConnection InSim connection to request packet from
     */
    protected NonRecurringPacketRequestBuilder(InSimConnection inSimConnection) {
        super(inSimConnection);
    }

    /**
     * Concludes building packet request, which is then registered in provided InSim connection.
     * That causes sending appropriate request packet.
     * @param callback method to be called when requested packet is received
     * @throws IOException if I/O error occurs when sending request packet
     */
    public void listen(PacketListener<T> callback) throws IOException {
        inSimConnection.request(buildPacketRequest(callback));
    }

    /**
     * Builds packet request to be registered in InSim connection.
     * @param callback method to be called when requested packet is received
     * @return packet request
     */
    protected abstract PacketRequest buildPacketRequest(PacketListener<T> callback);
}
