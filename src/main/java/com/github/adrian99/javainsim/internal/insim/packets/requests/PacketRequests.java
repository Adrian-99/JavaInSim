/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.internal.insim.packets.requests;

import com.github.adrian99.javainsim.internal.insim.packets.base.InfoPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.adrian99.javainsim.api.insim.InSimConnection;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * This class is a helper that manages {@link PacketRequest} instances.
 */
public class PacketRequests implements Closeable {
    private final Logger logger = LoggerFactory.getLogger(PacketRequests.class);
    private final Set<PacketRequest> pendingPacketRequests;
    private final Random random;
    private final long cleanUpIntervalMillis;
    private final ScheduledExecutorService executorService;
    private ScheduledFuture<?> cleanUpThread;

    /**
     * Creates {@link PacketRequest} helper.
     * @param cleanUpIntervalMillis interval (in milliseconds) in which checks for timed out packet requests
     *                              should be performed
     */
    public PacketRequests(long cleanUpIntervalMillis) {
        logger.debug("Initializing PacketRequests");
        this.pendingPacketRequests = new HashSet<>();
        this.random = new Random();
        this.cleanUpIntervalMillis = cleanUpIntervalMillis;
        this.executorService = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void close() throws IOException {
        logger.debug("Closing PacketRequests");
        executorService.shutdownNow();
    }

    /**
     * Adds specified packet request to list of pending packet requests.
     * @param packetRequest packet request to be added
     */
    public void add(PacketRequest packetRequest) {
        logger.debug("Adding new packet request");
        packetRequest.assignReqI(getFreeReqI(packetRequest.getRequestedPacketType()));
        pendingPacketRequests.add(packetRequest);
        tryToScheduleCleanUpThread();
    }

    /**
     * Checks whether any pending packet request matches with specified packet type and reqI value.
     * @param packetType packet type
     * @param reqI reqI value
     * @return whether any match was found
     */
    public boolean anyMatch(PacketType packetType, short reqI) {
        return pendingPacketRequests.stream().anyMatch(packetRequest -> packetRequest.matches(packetType, reqI));
    }

    /**
     * Tries to supply specified {@link InfoPacket} to pending packet requests. If needed, completed packet
     * requests are removed afterward.
     * @param inSimConnection InSim connection that packet was received from
     * @param receivedPacket received packet
     */
    public void handle(InSimConnection inSimConnection, InfoPacket receivedPacket) {
        var anyRequestRemoved = pendingPacketRequests.removeIf(
                packetRequest -> packetRequest.handleReceivedPacket(inSimConnection, receivedPacket)
        );
        if (anyRequestRemoved) {
            tryToStopCleanUpThread();
        }
    }

    /**
     * Cancels all pending recurring packet requests.
     * @throws IOException if I/O error occurs when cancelling recurring packet request
     */
    public void cancelAllRecurringPacketRequests() throws IOException {
        for (var packetRequest : pendingPacketRequests) {
            if (packetRequest instanceof RecurringPacketRequest<?> recurringPacketRequest && !recurringPacketRequest.isTimedOut()) {
                recurringPacketRequest.getActiveSubscription().cancel();
            }
        }
    }

    private short getFreeReqI(PacketType packetType) {
        var allowedReqIs = IntStream.range(1, 256).filter(
                reqI -> pendingPacketRequests.stream().noneMatch(
                        packetRequest -> packetRequest.matches(packetType, (short) reqI)
                )
        ).toArray();
        var reqIIndex = random.nextInt(0, allowedReqIs.length);
        return (short) allowedReqIs[reqIIndex];
    }

    private void tryToScheduleCleanUpThread() {
        if (cleanUpThread == null || cleanUpThread.isCancelled()) {
            logger.debug("Scheduling clean-up thread");
            cleanUpThread = executorService.scheduleAtFixedRate(
                    this::cleanUp,
                    cleanUpIntervalMillis,
                    cleanUpIntervalMillis,
                    TimeUnit.MILLISECONDS
            );
        }
    }

    private void cleanUp() {
        logger.debug("Cleaning-up timed out packet requests...");
        pendingPacketRequests.removeIf(PacketRequest::isTimedOut);
        tryToStopCleanUpThread();
    }

    private void tryToStopCleanUpThread() {
        if (pendingPacketRequests.isEmpty() && cleanUpThread != null && !cleanUpThread.isCancelled()) {
            logger.debug("Stopping clean-up thread - no pending packet requests left");
            cleanUpThread.cancel(false);
        }
    }
}
