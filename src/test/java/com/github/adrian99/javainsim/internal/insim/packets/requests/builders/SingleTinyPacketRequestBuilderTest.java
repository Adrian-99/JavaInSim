/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.internal.insim.packets.requests.builders;

import com.github.adrian99.javainsim.testutil.MockedInSimConnection;
import org.junit.jupiter.api.Test;
import com.github.adrian99.javainsim.api.insim.packets.SmallPacket;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.insim.packets.enums.SmallSubtype;
import com.github.adrian99.javainsim.api.insim.packets.enums.TinySubtype;
import com.github.adrian99.javainsim.internal.common.util.PacketDataBytes;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.github.adrian99.javainsim.testutil.TestUtils.byteToShort;

class SingleTinyPacketRequestBuilderTest {
    @Test
    void asCompletableFuture() throws IOException, ExecutionException, InterruptedException {
        var inSimConnectionMock = new MockedInSimConnection();
        var packetCompletableFuture = new SingleTinyPacketRequestBuilder<>(inSimConnectionMock, TinySubtype.GTH)
                .asCompletableFuture();

        var requestPacketBytes = inSimConnectionMock.assertAndGetSentPacketBytes();
        var reqI = requestPacketBytes[2];
        var expectedRequestPacketBytes = new byte[] { 1, 3, reqI, 8 };
        assertArrayEquals(expectedRequestPacketBytes, requestPacketBytes);

        var packetRequest = inSimConnectionMock.assertAndGetPacketRequest();
        packetRequest.handleReceivedPacket(
                inSimConnectionMock,
                new SmallPacket(byteToShort(reqI), new PacketDataBytes(new byte[] { 6, -80, 100, 5, 0 }))
        );

        var packet = packetCompletableFuture.get();
        assertEquals(8, packet.getSize());
        assertEquals(PacketType.SMALL, packet.getType());
        assertNotEquals(0, packet.getReqI());
        assertEquals(SmallSubtype.RTP, packet.getSubT());
        assertEquals(353456, packet.getUVal());
    }
}
