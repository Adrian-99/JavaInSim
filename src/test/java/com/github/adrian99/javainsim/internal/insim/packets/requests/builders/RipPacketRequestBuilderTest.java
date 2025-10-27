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
import com.github.adrian99.javainsim.api.common.flags.Flags;
import com.github.adrian99.javainsim.api.insim.packets.RipPacket;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.insim.packets.enums.RipErrorCode;
import com.github.adrian99.javainsim.api.insim.packets.flags.RipOption;
import com.github.adrian99.javainsim.internal.common.util.PacketDataBytes;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;
import static com.github.adrian99.javainsim.testutil.AssertionUtils.*;
import static com.github.adrian99.javainsim.testutil.TestUtils.byteToShort;

class RipPacketRequestBuilderTest {
    @Test
    void setRequestTimeout() throws IOException {
        var inSimConnectionMock = new MockedInSimConnection();
        new RipPacketRequestBuilder(inSimConnectionMock)
                .setRequestTimeout(500)
                .listen((inSimConnection, packet) -> {});

        var packetRequest = inSimConnectionMock.assertAndPopPacketRequest();

        assertConditionMet(packetRequest::isTimedOut, 1000, 100);
    }

    @Test
    void listenAsConfirmationFor() throws IOException {
        var receivedRipPacketsCount = new AtomicInteger();
        var inSimConnectionMock = new MockedInSimConnection();
        new RipPacketRequestBuilder(inSimConnectionMock)
                .asConfirmationFor(new RipPacket(
                        0,
                        false,
                        true,
                        new Flags<>(RipOption.LOOP, RipOption.FULL_PHYS),
                        631965,
                        "singleplayer_replay"
                ))
                .listen((inSimConnection, packet) -> {
                    assertEquals(inSimConnectionMock, inSimConnection);
                    assertEquals(80, packet.getSize());
                    assertEquals(PacketType.RIP, packet.getType());
                    assertNotEquals(0, packet.getReqI());
                    assertEquals(RipErrorCode.USER, packet.getError());
                    assertFalse(packet.isMpr());
                    assertFalse(packet.isPaused());
                    assertFlagsEqual(RipOption.class, Set.of(RipOption.FULL_PHYS), packet.getOptions());
                    assertEquals(1658315, packet.getCTime());
                    assertEquals(2060359, packet.getTTime());
                    assertEquals("singleplayer_replay", packet.getRName());
                    receivedRipPacketsCount.getAndIncrement();
                });

        var requestPacketBytes = inSimConnectionMock.assertAndPopSentPacketBytes();
        var reqI = requestPacketBytes[2];
        var expectedRequestPacketBytes = new byte[] {
                20, 48, reqI, 0, 0, 1, 5, 0, -99, -92, 9, 0, 0, 0, 0, 0,
                115, 105, 110, 103, 108, 101, 112, 108, 97, 121, 101, 114, 95, 114, 101, 112,
                108, 97, 121, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
        };
        assertArrayEquals(expectedRequestPacketBytes, requestPacketBytes);

        var packetRequest = inSimConnectionMock.assertAndPopPacketRequest();
        packetRequest.handleReceivedPacket(
                inSimConnectionMock,
                new RipPacket(byteToShort(reqI), new PacketDataBytes(new byte[] {
                        10, 0, 0, 4, 0, -53, 77, 25, 0, 71, 112, 31, 0, 115, 105, 110,
                        103, 108, 101, 112, 108, 97, 121, 101, 114, 95, 114, 101, 112, 108, 97, 121,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
                }))
        );

        assertConditionMet(() -> receivedRipPacketsCount.get() == 1, 5000, 100);
    }
}
