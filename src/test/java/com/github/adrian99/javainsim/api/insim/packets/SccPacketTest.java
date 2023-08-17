/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import org.junit.jupiter.api.Test;
import com.github.adrian99.javainsim.api.insim.packets.enums.ViewIdentifier;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class SccPacketTest {
    @Test
    void createSccPacket() {
        var packet = new SccPacket(25, ViewIdentifier.DRIVER);
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                2, 8, 0, 0, 25, 3, 0, 0
        };

        assertArrayEquals(expectedBytes, bytes);
    }
}
