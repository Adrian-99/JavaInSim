/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.api.common.flags.Flags;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketReader;
import com.github.adrian99.javainsim.testutil.AssertionUtils;
import com.github.adrian99.javainsim.testutil.MockedInSimConnection;
import org.junit.jupiter.api.Test;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.insim.packets.enums.RipErrorCode;
import com.github.adrian99.javainsim.api.insim.packets.flags.RipOption;

import java.io.IOException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static com.github.adrian99.javainsim.testutil.AssertionUtils.*;

class RipPacketTest {
    @Test
    void createRipPacket() {
        var packet = new RipPacket(
                58,
                true,
                false,
                new Flags<>(RipOption.SKINS),
                799534,
                "FE2R_Some_test_replay_name"
        );
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                20, 48, 58, 0, 1, 0, 2, 0, 46, 51, 12, 0, 0, 0, 0, 0,
                70, 69, 50, 82, 95, 83, 111, 109, 101, 95, 116, 101, 115, 116, 95, 114,
                101, 112, 108, 97, 121, 95, 110, 97, 109, 101, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
        };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void readRipPacket() {
        var headerBytes = new byte[] { 20, 48, -112 };
        var dataBytes = new byte[] {
                1, 0, 1, 1, 0, 37, -107, 2, 0, 84, 35, 45, 0, 65, 83, 53,
                88, 95, 82, 101, 112, 108, 97, 121, 70, 114, 111, 109, 65, 115, 116, 111,
                110, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
        };
        var packetReader = new PacketReader(headerBytes);

        assertPacketHeaderEquals(77, PacketType.RIP, 144, packetReader);

        var readPacket = packetReader.read(dataBytes);

        assertTrue(readPacket instanceof RipPacket);

        var castedReadPacket = (RipPacket) readPacket;

        assertPacketHeaderEquals(80, PacketType.RIP, 144, castedReadPacket);
        assertEquals(RipErrorCode.ALREADY, castedReadPacket.getError());
        assertFalse(castedReadPacket.isMpr());
        assertTrue(castedReadPacket.isPaused());
        AssertionUtils.assertFlagsEqual(RipOption.class, Set.of(RipOption.LOOP), castedReadPacket.getOptions());
        assertEquals(169253, castedReadPacket.getCTime());
        assertEquals(2958164, castedReadPacket.getTTime());
        assertEquals("AS5X_ReplayFromAston", castedReadPacket.getRName());
    }

    @Test
    void requestRipPacket() throws IOException {
        var inSimConnectionMock = new MockedInSimConnection();

        RipPacket.request(inSimConnectionMock).listen(((inSimConnection, packet) -> {}));

        var expectedRequestPacketBytes = new byte[] { 1, 3, 0, 22 };
        AssertionUtils.assertRequestPacketBytesEqual(expectedRequestPacketBytes, inSimConnectionMock.assertAndGetSentPacketBytes());
    }

    @Test
    void requestRipPacketAsConfirmationFor() throws IOException {
        var inSimConnectionMock = new MockedInSimConnection();

        RipPacket.request(inSimConnectionMock)
                .asConfirmationFor(new RipPacket(
                        15,
                        true,
                        false,
                        new Flags<>(RipOption.SKINS),
                        7437647,
                        "test_mpr"
                ))
                .listen(((inSimConnection, packet) -> {}));

        var expectedRequestPacketBytes = new byte[] {
                20, 48, 0, 0, 1, 0, 2, 0, 79, 125, 113, 0, 0, 0, 0, 0,
                116, 101, 115, 116, 95, 109, 112, 114, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
        };
        AssertionUtils.assertRequestPacketBytesEqual(expectedRequestPacketBytes, inSimConnectionMock.assertAndGetSentPacketBytes());
    }
}
