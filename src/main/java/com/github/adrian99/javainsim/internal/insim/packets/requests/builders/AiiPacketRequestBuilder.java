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
import com.github.adrian99.javainsim.api.insim.packets.AiiPacket;
import com.github.adrian99.javainsim.api.insim.packets.requests.SmallPacketRequest;
import com.github.adrian99.javainsim.api.insim.packets.subtypes.small.SmallSubtypes;
import com.github.adrian99.javainsim.internal.insim.packets.requests.PacketRequest;
import com.github.adrian99.javainsim.internal.insim.packets.requests.builders.base.NonRecurringPacketRequestBuilder;
import com.github.adrian99.javainsim.internal.insim.packets.requests.builders.base.SinglePacketRequestBuilder;

/**
 * Builder for {@link AiiPacket} request.
 */
public class AiiPacketRequestBuilder
        extends NonRecurringPacketRequestBuilder<AiiPacket>
        implements SinglePacketRequestBuilder<AiiPacket> {
    private final short plid;

    /**
     * Creates {@link AiiPacket} request.
     * @param inSimConnection InSim connection to request packet from
     * @param plid unique id of AI driver
     */
    public AiiPacketRequestBuilder(InSimConnection inSimConnection, short plid) {
        super(inSimConnection);
        this.plid = plid;
    }

    @Override
    protected PacketRequest buildPacketRequest(PacketListener<AiiPacket> callback) {
        return new SmallPacketRequest<>(SmallSubtypes.AII, plid, callback, requestTimeoutMillis);
    }
}
