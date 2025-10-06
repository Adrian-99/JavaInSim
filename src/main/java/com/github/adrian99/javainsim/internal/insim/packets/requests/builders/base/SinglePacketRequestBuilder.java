/*
 * Copyright (c) 2025, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.internal.insim.packets.requests.builders.base;

import com.github.adrian99.javainsim.api.insim.PacketListener;
import com.github.adrian99.javainsim.internal.insim.packets.base.RequestablePacket;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * Interface for builder of single packet request.
 * @param <T> type of packet to be requested
 */
public interface SinglePacketRequestBuilder<T extends RequestablePacket> {
    /**
     * Concludes building packet request, which is then registered in provided InSim connection.
     * That causes sending appropriate request packet.
     *
     * @return {@link CompletableFuture} that will complete with requested packet value when it is received
     * @throws IOException if I/O error occurs when sending request packet
     */
    default CompletableFuture<T> asCompletableFuture() throws IOException {
        var completableFuture = new CompletableFuture<T>();
        listen((inSimConnection, packet) -> completableFuture.complete(packet));
        return completableFuture;
    }

    /**
     * Concludes building packet request, which is then registered in provided InSim connection.
     * That causes sending appropriate request packet.
     * @param callback method to be called when requested packet is received
     * @throws IOException if I/O error occurs when sending request packet
     */
    void listen(PacketListener<T> callback) throws IOException;
}
