/*
 * Copyright (c) 2025, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.api.insim.packets.enums.AIInputType;
import com.github.adrian99.javainsim.api.insim.packets.structures.AIInputVal;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class AicPacketTest {
    @Test
    void createAicPacket() {
        var packet = new AicPacket(
                15,
                List.of(
                        new AIInputVal(AIInputType.IGNITION, 3),
                        new AIInputVal(AIInputType.HORN, 100, 1)
                )
        );
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] { 3, 68, 0, 15, 5, 0, 3, 0, 9, 100, 1, 0 };

        assertArrayEquals(expectedBytes, bytes);
    }
}
