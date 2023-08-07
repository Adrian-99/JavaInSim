/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.internal.insim.packets.requests.builders;

import org.junit.jupiter.api.Test;
import pl.adrian.api.insim.packets.SshPacket;
import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.api.insim.packets.enums.SshErrorCode;
import pl.adrian.internal.common.util.PacketDataBytes;
import pl.adrian.testutil.MockedInSimConnection;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;
import static pl.adrian.testutil.AssertionUtils.assertConditionMet;
import static pl.adrian.testutil.TestUtils.byteToShort;

class SshPacketRequestBuilderTest {
    @Test
    void listen() throws IOException {
        var receivedSshPacketsCount = new AtomicInteger();
        var inSimConnectionMock = new MockedInSimConnection();
        new SshPacketRequestBuilder(inSimConnectionMock, new SshPacket(0, "new_screenshot"))
                .listen((inSimConnection, packet) -> {
                    assertEquals(inSimConnectionMock, inSimConnection);
                    assertEquals(40, packet.getSize());
                    assertEquals(PacketType.SSH, packet.getType());
                    assertNotEquals(0, packet.getReqI());
                    assertEquals(SshErrorCode.DEDICATED, packet.getError());
                    assertEquals("new_screenshot", packet.getName());
                    receivedSshPacketsCount.getAndIncrement();
                });

        var requestPacketBytes = inSimConnectionMock.assertAndGetSentPacketBytes();
        var reqI = requestPacketBytes[2];
        var expectedRequestPacketBytes = new byte[] {
                10, 49, reqI, 0, 0, 0, 0, 0, 110, 101, 119, 95, 115, 99, 114, 101,
                101, 110, 115, 104, 111, 116, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0
        };
        assertArrayEquals(expectedRequestPacketBytes, requestPacketBytes);

        var packetRequest = inSimConnectionMock.assertAndGetPacketRequest();
        packetRequest.handleReceivedPacket(
                inSimConnectionMock,
                new SshPacket(byteToShort(reqI), new PacketDataBytes(new byte[] {
                        1, 0, 0, 0, 0, 110, 101, 119, 95, 115, 99, 114, 101, 101, 110, 115,
                        104, 111, 116, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0
                }))
        );

        assertConditionMet(() -> receivedSshPacketsCount.get() == 1, 5000, 100);
    }

    @Test
    void asCompletableFuture() throws IOException, ExecutionException, InterruptedException {
        var inSimConnectionMock = new MockedInSimConnection();
        var packetCompletableFuture = new SshPacketRequestBuilder(
                inSimConnectionMock,
                new SshPacket(0, "new_screenshot")
        ).asCompletableFuture();

        var requestPacketBytes = inSimConnectionMock.assertAndGetSentPacketBytes();
        var reqI = requestPacketBytes[2];
        var expectedRequestPacketBytes = new byte[] {
                10, 49, reqI, 0, 0, 0, 0, 0, 110, 101, 119, 95, 115, 99, 114, 101,
                101, 110, 115, 104, 111, 116, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0
        };
        assertArrayEquals(expectedRequestPacketBytes, requestPacketBytes);

        var packetRequest = inSimConnectionMock.assertAndGetPacketRequest();
        packetRequest.handleReceivedPacket(
                inSimConnectionMock,
                new SshPacket(byteToShort(reqI), new PacketDataBytes(new byte[] {
                        1, 0, 0, 0, 0, 110, 101, 119, 95, 115, 99, 114, 101, 101, 110, 115,
                        104, 111, 116, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0
                }))
        );

        var packet = packetCompletableFuture.get();
        assertEquals(40, packet.getSize());
        assertEquals(PacketType.SSH, packet.getType());
        assertNotEquals(0, packet.getReqI());
        assertEquals(SshErrorCode.DEDICATED, packet.getError());
        assertEquals("new_screenshot", packet.getName());
    }
}
