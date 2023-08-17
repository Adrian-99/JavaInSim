/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import org.junit.jupiter.api.Test;
import com.github.adrian99.javainsim.api.insim.packets.enums.MessageSound;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class MslPacketTest {
    @Test
    void createMslPacket() {
        var packet = new MslPacket(MessageSound.SILENT, "message that will appear only locally");
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                33, 40, 0, 0, 109, 101, 115, 115, 97, 103, 101, 32, 116, 104, 97, 116,
                32, 119, 105, 108, 108, 32, 97, 112, 112, 101, 97, 114, 32, 111, 110, 108,
                121, 32, 108, 111, 99, 97, 108, 108, 121, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0
        };

        assertArrayEquals(expectedBytes, bytes);
    }
}
