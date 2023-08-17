/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.api.common.flags.Flags;
import org.junit.jupiter.api.Test;
import com.github.adrian99.javainsim.api.insim.packets.flags.IsiFlag;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class IsiPacketTest {

    @Test
    void createIsiPacket() {
        var packet = new IsiPacket(
                3000,
                new Flags<>(IsiFlag.LOCAL, IsiFlag.MSO_COLS, IsiFlag.MCI, IsiFlag.AXM_LOAD),
                '!',
                500,
                "admin123",
                "test app"
        );
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                11, 1, 1, 0, -72, 11, 44, 2, 9, 33, -12, 1, 97, 100, 109, 105,
                110, 49, 50, 51, 0, 0, 0, 0, 0, 0, 0, 0, 116, 101, 115, 116,
                32, 97, 112, 112, 0, 0, 0, 0, 0, 0, 0, 0
        };

        assertArrayEquals(expectedBytes, bytes);
    }
}
