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
import com.github.adrian99.javainsim.api.insim.packets.TinyPacket;
import com.github.adrian99.javainsim.api.insim.packets.subtypes.tiny.TinyRequestingSubtype;
import com.github.adrian99.javainsim.api.insim.packets.requests.TinyPacketRequest;
import com.github.adrian99.javainsim.internal.insim.packets.base.RequestablePacket;
import com.github.adrian99.javainsim.internal.insim.packets.requests.PacketRequest;
import com.github.adrian99.javainsim.internal.insim.packets.requests.builders.base.BasePacketRequestBuilder;

/**
 * Basic builder of packet request where {@link TinyPacket} serves as a request. Both single and multiple packet
 * responses are allowed.
 * @param <T> type of packet to be requested
 */
public class TinyPacketRequestBuilder<T extends RequestablePacket> extends BasePacketRequestBuilder<T> {
    private final TinyRequestingSubtype<T> tinySubtype;

    /**
     * Creates basic packet request builder.
     * @param inSimConnection InSim connection to request packet from
     * @param tinySubtype subtype to be used in request packet
     */
    public TinyPacketRequestBuilder(InSimConnection inSimConnection, TinyRequestingSubtype<T> tinySubtype) {
        super(inSimConnection);
        this.tinySubtype = tinySubtype;
    }

    @Override
    protected PacketRequest buildPacketRequest(PacketListener<T> callback) {
        return new TinyPacketRequest<>(tinySubtype, callback, requestTimeoutMillis);
    }
}
