/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.insim.packets;

import org.junit.jupiter.api.Test;
import pl.adrian.api.common.enums.DefaultCar;
import pl.adrian.api.common.flags.Flags;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class PlcPacketTest {
    @Test
    void createPlcPacket() {
        var packet = new PlcPacket(21, new Flags<>(DefaultCar.RB4, DefaultCar.FXO, DefaultCar.XRT));
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] { 3, 53, 0, 0, 21, 0, 0, 0, 28, 0, 0, 0 };

        assertArrayEquals(expectedBytes, bytes);
    }
}
