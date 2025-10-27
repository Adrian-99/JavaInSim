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
import com.github.adrian99.javainsim.internal.insim.packets.base.InstructionPacket;
import com.github.adrian99.javainsim.internal.insim.packets.base.RequestablePacket;
import com.github.adrian99.javainsim.api.insim.packets.requests.RecurringPacketSubscription;
import com.github.adrian99.javainsim.internal.insim.packets.requests.RecurringPacketRequest;

import java.io.IOException;
import java.util.function.Function;

/**
 * Basic builder of recurring packet request.
 * @param <T> type of packet to be requested
 */
public abstract class RecurringPacketRequestBuilder<T extends RequestablePacket> extends BasePacketRequestBuilder {
    /**
     * Function that returns {@link InstructionPacket} serving as cancelling packet.
     * The function parameter is the reqI value that should be included in cancelling packet.
     */
    protected final Function<Short, InstructionPacket> cancellingPacketBuilder;

    /**
     * Creates basic builder of packet request.
     * @param inSimConnection InSim connection to request packet from
     * @param cancellingPacketBuilder function that returns {@link InstructionPacket} serving as cancelling packet
     */
    protected RecurringPacketRequestBuilder(InSimConnection inSimConnection,
                                            Function<Short, InstructionPacket> cancellingPacketBuilder) {
        super(inSimConnection);
        this.cancellingPacketBuilder = cancellingPacketBuilder;
    }

    /**
     * Concludes building packet request, which is then registered in provided InSim connection.
     * That causes sending appropriate request packet.
     * @param callback method to be called when requested packet is received
     * @return subscription related to the request
     * @throws IOException if I/O error occurs when sending request packet
     */
    public RecurringPacketSubscription subscribe(PacketListener<T> callback) throws IOException {
        var request = buildPacketRequest(callback);
        inSimConnection.request(request);
        return request.getActiveSubscription();
    }

    /**
     * Builds packet request to be registered in InSim connection.
     * @param callback method to be called when requested packet is received
     * @return packet request
     */
    protected abstract RecurringPacketRequest<T> buildPacketRequest(PacketListener<T> callback);
}
