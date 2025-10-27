/*
 * Copyright (c) 2025, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.internal.insim.packets.requests.builders;

import com.github.adrian99.javainsim.api.insim.InSimConnection;
import com.github.adrian99.javainsim.api.insim.PacketListener;
import com.github.adrian99.javainsim.api.insim.packets.AxmPacket;
import com.github.adrian99.javainsim.api.insim.packets.TtcPacket;
import com.github.adrian99.javainsim.api.insim.packets.requests.RecurringTtcPacketRequest;
import com.github.adrian99.javainsim.api.insim.packets.subtypes.ttc.TtcRequestingSubtype;
import com.github.adrian99.javainsim.internal.insim.packets.base.InstructionPacket;
import com.github.adrian99.javainsim.internal.insim.packets.base.RequestablePacket;
import com.github.adrian99.javainsim.internal.insim.packets.requests.RecurringPacketRequest;
import com.github.adrian99.javainsim.internal.insim.packets.requests.builders.base.RecurringPacketRequestBuilder;

import java.util.function.Function;

/**
 * Builder of recurring packet request where {@link TtcPacket} serves as a request. Currently only {@link AxmPacket}
 * can be requested this way.
 */
public class RecurringTtcPacketRequestBuilder<T extends RequestablePacket>
        extends RecurringPacketRequestBuilder<T> {
    private final TtcRequestingSubtype<T> subtype;
    private final int ucid;
    private final int b1;
    private final int b2;
    private final int b3;

    /**
     * Creates recurring packet request builder.
     * @param inSimConnection InSim connection to request packet from
     * @param ttcSubtype subtype to be used in request packet
     * @param ucid unique connection id (0 = local / non-zero = guest)
     * @param b1Value additional value 1 to be sent in request packet
     * @param b2Value additional value 2 to be sent in request packet
     * @param b3Value additional value 3 to be sent in request packet
     * @param cancellingPacketBuilder function that returns {@link InstructionPacket} serving as cancelling packet
     */
    public RecurringTtcPacketRequestBuilder(InSimConnection inSimConnection,
                                            TtcRequestingSubtype<T> ttcSubtype,
                                            int ucid,
                                            int b1Value,
                                            int b2Value,
                                            int b3Value,
                                            Function<Short, InstructionPacket> cancellingPacketBuilder) {
        super(inSimConnection, cancellingPacketBuilder);
        this.subtype = ttcSubtype;
        this.ucid = ucid;
        this.b1 = b1Value;
        this.b2 = b2Value;
        this.b3 = b3Value;
    }


    @Override
    protected RecurringPacketRequest<T> buildPacketRequest(PacketListener<T> callback) {
        return new RecurringTtcPacketRequest<>(inSimConnection, subtype, ucid, b1, b2, b3, callback, cancellingPacketBuilder);
    }
}
