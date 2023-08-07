/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.insim.packets;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class MsxPacketTest {
    @Test
    void createMsxPacket() {
        var packet = new MsxPacket("test message to be sent, the message has more than 64 characters already");
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                25, 39, 0, 0, 116, 101, 115, 116, 32, 109, 101, 115, 115, 97, 103, 101,
                32, 116, 111, 32, 98, 101, 32, 115, 101, 110, 116, 44, 32, 116, 104, 101,
                32, 109, 101, 115, 115, 97, 103, 101, 32, 104, 97, 115, 32, 109, 111, 114,
                101, 32, 116, 104, 97, 110, 32, 54, 52, 32, 99, 104, 97, 114, 97, 99,
                116, 101, 114, 115, 32, 97, 108, 114, 101, 97, 100, 121, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0
        };

        assertArrayEquals(expectedBytes, bytes);
    }
}
