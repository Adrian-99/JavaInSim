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
import com.github.adrian99.javainsim.api.insim.packets.flags.SchFlag;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class SchPacketTest {
    @Test
    void createSchPacket() {
        var packet = new SchPacket('A', new Flags<>(SchFlag.SHIFT, SchFlag.CTRL));
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                2, 6, 0, 0, 65, 3, 0, 0
        };

        assertArrayEquals(expectedBytes, bytes);
    }
}
