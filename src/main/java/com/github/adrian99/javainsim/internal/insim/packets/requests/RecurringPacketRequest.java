/*
 * Copyright (c) 2025, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.internal.insim.packets.requests;

import com.github.adrian99.javainsim.api.insim.InSimConnection;
import com.github.adrian99.javainsim.api.insim.PacketListener;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.internal.insim.packets.base.InstructionPacket;
import com.github.adrian99.javainsim.internal.insim.packets.base.RequestablePacket;
import com.github.adrian99.javainsim.api.insim.packets.requests.RecurringPacketSubscription;
import org.slf4j.Logger;

import java.util.function.Function;

/**
 * This class serves as a base for all recurring packet requests. It is extension of {@link AbstractPacketRequest}.
 * @param <T> type of packet this request describes
 */
public abstract class RecurringPacketRequest<T extends RequestablePacket> extends AbstractPacketRequest<T> {
    private final InSimConnection inSimConnection;
    private final Function<Short, InstructionPacket> cancellingPacketBuilder;

    private RecurringPacketSubscription activeSubscription;

    /**
     * Creates recurring packet request implementation.
     * @param inSimConnection InSim connection that the packet will be requested from
     * @param logger logger to be used within packet request
     * @param requestedPacketType type of packet this request describes
     * @param callback method to be called when requested packet is received
     * @param cancellingPacketBuilder function that returns {@link InstructionPacket} serving as cancelling packet
     */
    protected RecurringPacketRequest(InSimConnection inSimConnection,
                                     Logger logger,
                                     PacketType requestedPacketType,
                                     PacketListener<T> callback,
                                     Function<Short, InstructionPacket> cancellingPacketBuilder) {
        super(logger, requestedPacketType, false, callback, 0);
        this.inSimConnection = inSimConnection;
        this.cancellingPacketBuilder = cancellingPacketBuilder;
    }

    @Override
    public void assignReqI(short reqI) {
        activeSubscription = new RecurringPacketSubscription(inSimConnection, cancellingPacketBuilder.apply(reqI));
        super.assignReqI(reqI);
    }

    @Override
    public boolean isTimedOut() {
        return activeSubscription != null && activeSubscription.isCancelled();
    }

    /**
     * @return active subscription related to this request
     */
    public RecurringPacketSubscription getActiveSubscription() {
        return activeSubscription;
    }
}
