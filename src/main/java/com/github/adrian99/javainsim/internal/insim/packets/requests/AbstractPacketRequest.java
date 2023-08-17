/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.internal.insim.packets.requests;

import com.github.adrian99.javainsim.api.insim.InSimConnection;
import com.github.adrian99.javainsim.internal.common.util.LoggerUtils;
import com.github.adrian99.javainsim.internal.insim.packets.base.InfoPacket;
import com.github.adrian99.javainsim.internal.insim.packets.base.InstructionPacket;
import org.slf4j.Logger;
import com.github.adrian99.javainsim.api.insim.PacketListener;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.internal.insim.packets.base.RequestablePacket;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * This class provides basic implementations of methods from {@link PacketRequest} interface.
 * @param <T> type of packet this request describes
 */
public abstract class AbstractPacketRequest<T extends RequestablePacket> implements PacketRequest {
    private final Logger logger;
    private final PacketType requestedPacketType;
    private final boolean singlePacketResponse;
    private final PacketListener<T> callback;
    private final long timoutMillis;
    private InstructionPacket requestPacket;
    private LocalDateTime lastUpdate;

    /**
     * Creates basic packet request implementation.
     * @param logger logger to be used within packet request
     * @param requestedPacketType type of packet this request describes
     * @param singlePacketResponse whether single packet response is expected
     * @param callback method to be called when requested packet is received
     * @param timeoutMillis period of time (in milliseconds) after which packet request should be considered timed out
     */
    protected AbstractPacketRequest(Logger logger,
                                    PacketType requestedPacketType,
                                    boolean singlePacketResponse,
                                    PacketListener<T> callback,
                                    long timeoutMillis) {
        this.logger = logger;
        this.requestedPacketType = requestedPacketType;
        this.singlePacketResponse = singlePacketResponse;
        this.callback = callback;
        this.timoutMillis = timeoutMillis;
        lastUpdate = LocalDateTime.now();
    }

    /**
     * Creates packet that serves as a request.
     * @param reqI reqI value to be used by request packet
     * @return created request packet
     */
    protected abstract InstructionPacket createRequestPacket(short reqI);

    @Override
    public void assignReqI(short reqI) {
        requestPacket = createRequestPacket(reqI);
    }

    @Override
    public PacketType getRequestedPacketType() {
        return requestedPacketType;
    }

    @Override
    public InstructionPacket getRequestPacket() {
        return requestPacket;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean handleReceivedPacket(InSimConnection inSimConnection, InfoPacket receivedPacket) {
        if (matches(receivedPacket.getType(), receivedPacket.getReqI())) {
            try {
                logger.debug("Requested packet received");
                callback.onPacketReceived(inSimConnection, (T) receivedPacket);
            } catch (Exception exception) {
                logger.error("Error occurred in packet request callback: {}", exception.getMessage());
                LoggerUtils.logStacktrace(logger, "packet request callback", exception);
            }
            lastUpdate = LocalDateTime.now();
            return singlePacketResponse;
        }
        return false;
    }

    @Override
    public boolean matches(PacketType packetType, short reqI) {
        return requestedPacketType.equals(packetType) && requestPacket.getReqI() == reqI;
    }

    @Override
    public boolean isTimedOut() {
        return lastUpdate.until(LocalDateTime.now(), ChronoUnit.MILLIS) > timoutMillis;
    }
}
