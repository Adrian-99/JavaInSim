/*
 * Copyright (c) 2025, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.requests;

import com.github.adrian99.javainsim.api.insim.packets.TinyPacket;
import com.github.adrian99.javainsim.api.insim.packets.subtypes.tiny.TinySubtypes;
import com.github.adrian99.javainsim.testutil.MockedInSimConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class RecurringPacketSubscriptionTest {
    private MockedInSimConnection inSimConnectionMock;

    @BeforeEach
    void beforeEach() throws IOException {
        inSimConnectionMock = new MockedInSimConnection();
    }

    @Test
    void cancel() throws IOException {
        var subscription = new RecurringPacketSubscription(inSimConnectionMock, new TinyPacket(2, TinySubtypes.RIP));

        assertFalse(subscription.isCancelled());

        subscription.cancel();

        assertTrue(subscription.isCancelled());

        var expectedCancellingPacketBytes = new byte[] { 1, 3, 2, 22 };
        assertArrayEquals(expectedCancellingPacketBytes, inSimConnectionMock.assertAndPopSentPacketBytes());
    }

    @Test
    void cancelTwice() throws IOException {
        var subscription = new RecurringPacketSubscription(inSimConnectionMock, new TinyPacket(2, TinySubtypes.RIP));
        subscription.cancel();

        assertTrue(subscription.isCancelled());

        assertThrows(IllegalStateException.class, subscription::cancel);
    }
}
