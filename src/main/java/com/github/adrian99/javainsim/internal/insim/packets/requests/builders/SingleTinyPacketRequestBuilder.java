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
import com.github.adrian99.javainsim.api.insim.packets.enums.TinySubtype;
import com.github.adrian99.javainsim.internal.insim.packets.base.RequestablePacket;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * Builder of packet request where {@link TinyPacket} serves as a request. Only single packet response is allowed.
 * @param <T> type of packet to be requested
 */
public class SingleTinyPacketRequestBuilder<T extends RequestablePacket> extends BasicTinyPacketRequestBuilder<T> {
    /**
     * Creates packet request builder.
     * @param inSimConnection InSim connection to request packet from
     * @param tinySubtype subtype to be used in request packet
     */
    public SingleTinyPacketRequestBuilder(InSimConnection inSimConnection, TinySubtype.Requesting<T> tinySubtype) {
        super(inSimConnection, tinySubtype);
    }

    /**
     * Concludes building packet request, which is then registered in provided InSim connection.
     * That causes sending appropriate {@link TinyPacket} that serves as a request.
     * @return {@link CompletableFuture} that will complete with requested packet value when it is received
     * @throws IOException if I/O error occurs when sending request packet
     */
    public CompletableFuture<T> asCompletableFuture() throws IOException {
        var completableFuture = new CompletableFuture<T>();
        listen((inSimConnection, packet) -> completableFuture.complete(packet));
        return completableFuture;
    }
}
