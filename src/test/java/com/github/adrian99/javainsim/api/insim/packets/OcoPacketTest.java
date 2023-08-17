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
import com.github.adrian99.javainsim.api.insim.packets.enums.OcoAction;
import com.github.adrian99.javainsim.api.insim.packets.enums.StartLightsIndex;
import com.github.adrian99.javainsim.api.insim.packets.flags.StartLightsDataFlag;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class OcoPacketTest {
    @Test
    void createOcoPacket() {
        var packet = new OcoPacket(
                OcoAction.LIGHTS_SET,
                StartLightsIndex.START_LIGHTS,
                55,
                new Flags<>(StartLightsDataFlag.RED1, StartLightsDataFlag.RED2_AMBER)
        );
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] { 2, 60, 0, 0, 5, -107, 55, 3 };

        assertArrayEquals(expectedBytes, bytes);
    }
}
