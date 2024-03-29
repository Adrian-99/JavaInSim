/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.internal.insim.packets.requests.builders;

import com.github.adrian99.javainsim.api.insim.packets.enums.SmallSubtype;
import com.github.adrian99.javainsim.internal.common.util.PacketDataBytes;
import com.github.adrian99.javainsim.testutil.AssertionUtils;
import com.github.adrian99.javainsim.testutil.MockedInSimConnection;
import com.github.adrian99.javainsim.testutil.TestUtils;
import org.junit.jupiter.api.Test;
import com.github.adrian99.javainsim.api.insim.packets.SmallPacket;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.insim.packets.enums.TinySubtype;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class BasicTinyPacketRequestBuilderTest {
    @Test
    void listen() throws IOException {
        var receivedSmallPacketsCount = new AtomicInteger();
        var inSimConnectionMock = new MockedInSimConnection();

        new BasicTinyPacketRequestBuilder<>(inSimConnectionMock, TinySubtype.GTH)
                .listen((connection, packet) -> {
                    assertEquals(inSimConnectionMock, connection);
                    assertEquals(8, packet.getSize());
                    assertEquals(PacketType.SMALL, packet.getType());
                    assertNotEquals(0, packet.getReqI());
                    assertEquals(SmallSubtype.RTP, packet.getSubT());
                    assertEquals(353456, packet.getUVal());
                    receivedSmallPacketsCount.getAndIncrement();
                });


        var requestPacketBytes = inSimConnectionMock.assertAndGetSentPacketBytes();
        var reqI = requestPacketBytes[2];
        var expectedRequestPacketBytes = new byte[] { 1, 3, reqI, 8 };
        assertArrayEquals(expectedRequestPacketBytes, requestPacketBytes);

        var packetRequest = inSimConnectionMock.assertAndGetPacketRequest();
        packetRequest.handleReceivedPacket(
                inSimConnectionMock,
                new SmallPacket(TestUtils.byteToShort(reqI), new PacketDataBytes(new byte[] { 6, -80, 100, 5, 0 }))
        );

        AssertionUtils.assertConditionMet(() -> receivedSmallPacketsCount.get() == 1, 5000, 100);
    }
}
