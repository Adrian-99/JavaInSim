/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.insim.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.common.flags.Flags;
import pl.adrian.api.insim.packets.flags.SchFlag;

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
