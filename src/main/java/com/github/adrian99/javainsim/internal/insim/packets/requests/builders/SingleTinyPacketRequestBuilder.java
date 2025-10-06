/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.internal.insim.packets.requests.builders;

import com.github.adrian99.javainsim.api.insim.InSimConnection;
import com.github.adrian99.javainsim.api.insim.packets.TinyPacket;
import com.github.adrian99.javainsim.api.insim.packets.subtypes.tiny.TinyRequestingSubtype;
import com.github.adrian99.javainsim.internal.insim.packets.base.RequestablePacket;
import com.github.adrian99.javainsim.internal.insim.packets.requests.builders.base.SinglePacketRequestBuilder;

/**
 * Builder of packet request where {@link TinyPacket} serves as a request. Only single packet response is allowed.
 * @param <T> type of packet to be requested
 */
public class SingleTinyPacketRequestBuilder<T extends RequestablePacket>
        extends TinyPacketRequestBuilder<T>
        implements SinglePacketRequestBuilder<T> {
    /**
     * Creates packet request builder.
     * @param inSimConnection InSim connection to request packet from
     * @param tinySubtype subtype to be used in request packet
     */
    public SingleTinyPacketRequestBuilder(InSimConnection inSimConnection, TinyRequestingSubtype<T> tinySubtype) {
        super(inSimConnection, tinySubtype);
    }
}
