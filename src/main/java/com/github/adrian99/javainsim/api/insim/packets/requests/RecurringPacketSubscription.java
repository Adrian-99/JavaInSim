/*
 * Copyright (c) 2025, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.requests;

import com.github.adrian99.javainsim.api.insim.InSimConnection;
import com.github.adrian99.javainsim.internal.insim.packets.base.InstructionPacket;

import java.io.IOException;

/**
 * This class represents recurring packet subscription.
 * The earlier requested recurring packets can be cancelled using {@link #cancel()} method.
 */
public class RecurringPacketSubscription {
    private final InSimConnection inSimConnection;
    private final InstructionPacket cancellingPacket;

    private boolean isCancelled;

    /**
     * Creates recurring packet subscription. Constructor used only internally.
     * @param inSimConnection InSim connection that the packets were requested from
     * @param cancellingPacket packet that, when sent, will cancel recurring packets
     */
    public RecurringPacketSubscription(InSimConnection inSimConnection,
                                       InstructionPacket cancellingPacket) {
        this.inSimConnection = inSimConnection;
        this.cancellingPacket = cancellingPacket;
        this.isCancelled = false;
    }

    /**
     * @return whether this subscription was already cancelled
     */
    public boolean isCancelled() {
        return isCancelled;
    }

    /**
     * Sends cancelling packet to LFS.
     * As a result, no more recurring packets related to this subscription will be sent from LFS.
     * @throws IOException if I/O error occurs when cancelling recurring packet request
     * @throws IllegalStateException if this subscription was already cancelled
     */
    public void cancel() throws IOException {
        if (!isCancelled) {
            isCancelled = true;
            inSimConnection.send(cancellingPacket);
        } else {
            throw new IllegalStateException("Subscription is already cancelled");
        }
    }
}
