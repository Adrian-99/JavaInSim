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
import com.github.adrian99.javainsim.api.insim.packets.enums.TinySubtype;
import com.github.adrian99.javainsim.internal.insim.packets.base.RequestablePacket;
import com.github.adrian99.javainsim.api.insim.packets.requests.TinyPacketRequest;

import java.io.IOException;

/**
 * Basic builder of packet request where {@link TinyPacket} serves as a request. Both single and multiple packet
 * responses are allowed.
 * @param <T> type of packet to be requested
 */
public class BasicTinyPacketRequestBuilder<T extends RequestablePacket> {
    private final TinySubtype.Requesting<T> tinySubtype;
    /**
     * InSim connection to request packet from
     */
    protected final InSimConnection inSimConnection;
    /**
     * timeout (in milliseconds) of the packet request - can be overridden by inheritors
     */
    protected long requestTimeoutMillis = 5000;

    /**
     * Creates basic packet request builder.
     * @param inSimConnection InSim connection to request packet from
     * @param tinySubtype subtype to be used in request packet
     */
    public BasicTinyPacketRequestBuilder(InSimConnection inSimConnection, TinySubtype.Requesting<T> tinySubtype) {
        this.tinySubtype = tinySubtype;
        this.inSimConnection = inSimConnection;
    }

    /**
     * Concludes building packet request, which is then registered in provided InSim connection.
     * That causes sending appropriate {@link TinyPacket} that serves as a request.
     * @param callback method to be called when requested packet is received
     * @throws IOException if I/O error occurs when sending request packet
     */
    public void listen(PacketListener<T> callback) throws IOException {
        inSimConnection.request(new TinyPacketRequest<>(tinySubtype, callback, requestTimeoutMillis));
    }
}
