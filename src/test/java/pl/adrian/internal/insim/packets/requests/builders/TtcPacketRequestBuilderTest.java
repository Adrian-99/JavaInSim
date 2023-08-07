/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.internal.insim.packets.requests.builders;

import org.junit.jupiter.api.Test;
import pl.adrian.api.insim.packets.AxmPacket;
import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.api.insim.packets.enums.PmoAction;
import pl.adrian.api.insim.packets.flags.PmoFlag;
import pl.adrian.internal.common.util.PacketDataBytes;
import pl.adrian.testutil.MockedInSimConnection;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;
import static pl.adrian.testutil.AssertionUtils.assertConditionMet;
import static pl.adrian.testutil.AssertionUtils.assertFlagsEqual;
import static pl.adrian.testutil.TestUtils.byteToShort;

class TtcPacketRequestBuilderTest {
    @Test
    void listen() throws IOException {
        var receivedAxmPacketsCount = new AtomicInteger();
        var inSimConnectionMock = new MockedInSimConnection();

        new TtcPacketRequestBuilder(inSimConnectionMock, 29)
                .listen((inSimConnection, packet) -> {
                    assertEquals(inSimConnectionMock, inSimConnection);
                    assertEquals(8, packet.getSize());
                    assertEquals(PacketType.AXM, packet.getType());
                    assertNotEquals(0, packet.getReqI());
                    assertEquals(0, packet.getNumO());
                    assertEquals(0, packet.getUcid());
                    assertEquals(PmoAction.TTC_SEL, packet.getPmoAction());
                    assertFlagsEqual(PmoFlag.class, Set.of(), packet.getPmoFlags());
                    assertEquals(0, packet.getInfo().size());
                    receivedAxmPacketsCount.getAndIncrement();
                });

        var requestPacketBytes = inSimConnectionMock.assertAndGetSentPacketBytes();
        var reqI = requestPacketBytes[2];
        var expectedRequestPacketBytes = new byte[] { 2, 61, reqI, 1, 29, 0, 0, 0 };
        assertArrayEquals(expectedRequestPacketBytes, requestPacketBytes);

        var packetRequest = inSimConnectionMock.assertAndGetPacketRequest();
        packetRequest.handleReceivedPacket(
                inSimConnectionMock,
                new AxmPacket((short) 8, byteToShort(reqI), new PacketDataBytes(new byte[] { 0, 0, 5, 0, 0 }))
        );

        assertConditionMet(() -> receivedAxmPacketsCount.get() == 1, 5000, 100);
    }

    @Test
    void asCompletableFuture() throws IOException, ExecutionException, InterruptedException {
        var inSimConnectionMock = new MockedInSimConnection();

        var packetCompletableFuture = new TtcPacketRequestBuilder(inSimConnectionMock, 29)
                .asCompletableFuture();

        var requestPacketBytes = inSimConnectionMock.assertAndGetSentPacketBytes();
        var reqI = requestPacketBytes[2];
        var expectedRequestPacketBytes = new byte[] { 2, 61, reqI, 1, 29, 0, 0, 0 };
        assertArrayEquals(expectedRequestPacketBytes, requestPacketBytes);

        var packetRequest = inSimConnectionMock.assertAndGetPacketRequest();
        packetRequest.handleReceivedPacket(
                inSimConnectionMock,
                new AxmPacket((short) 8, byteToShort(reqI), new PacketDataBytes(new byte[] { 0, 0, 5, 0, 0 }))
        );

        var packet = packetCompletableFuture.get();
        assertEquals(8, packet.getSize());
        assertEquals(PacketType.AXM, packet.getType());
        assertNotEquals(0, packet.getReqI());
        assertEquals(0, packet.getNumO());
        assertEquals(0, packet.getUcid());
        assertEquals(PmoAction.TTC_SEL, packet.getPmoAction());
        assertFlagsEqual(PmoFlag.class, Set.of(), packet.getPmoFlags());
        assertEquals(0, packet.getInfo().size());
    }
}
