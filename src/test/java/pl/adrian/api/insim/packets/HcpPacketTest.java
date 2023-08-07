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
import pl.adrian.api.insim.packets.structures.CarHandicaps;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class HcpPacketTest {
    @Test
    void createHcpPacket() {
        var packet = new HcpPacket(Map.of(
                DefaultCar.LX6, new CarHandicaps(5, 15),
                DefaultCar.RAC, new CarHandicaps(15, 20),
                DefaultCar.FZ5, new CarHandicaps(15, 25)
        ));
        var bytes = packet.getBytes();
        var expectedBytes = new byte[] {
                17, 56, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                5, 15, 0, 0, 0, 0, 15, 20, 15, 25, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0
        };

        assertArrayEquals(expectedBytes, bytes);
    }
}
