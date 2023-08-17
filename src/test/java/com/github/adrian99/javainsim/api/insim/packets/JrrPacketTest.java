/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.api.insim.packets.structures.objectinfo.PositionObjectInfo;
import org.junit.jupiter.api.Test;
import com.github.adrian99.javainsim.api.insim.packets.enums.JrrAction;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class JrrPacketTest {
    @Test
    void createJrrPacket() {
        var packet = new JrrPacket(
                16,
                28,
                JrrAction.SPAWN,
                new PositionObjectInfo(-53, 954, 2, 221)
        );
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                4, 58, 0, 16, 28, 1, 0, 0, -53, -1, -70, 3, 2, -128, 0, -35
        };

        assertArrayEquals(expectedBytes, bytes);
    }

    @Test
    void createJrrPacket_withoutStartPos() {
        var packet = new JrrPacket(16, 28, JrrAction.REJECT);
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                4, 58, 0, 16, 28, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
        };

        assertArrayEquals(expectedBytes, bytes);
    }
}
