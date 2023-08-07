/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.outsim.structures;

import pl.adrian.internal.common.util.PacketDataBytes;

/**
 * Structure for wheels.
 */
public class OutSimWheels {
    private final OutSimWheel leftRear;
    private final OutSimWheel rightRear;
    private final OutSimWheel leftFront;
    private final OutSimWheel rightFront;

    /**
     * Creates structure for wheels.
     * @param packetDataBytes packet data bytes
     */
    public OutSimWheels(PacketDataBytes packetDataBytes) {
        leftRear = new OutSimWheel(packetDataBytes);
        rightRear = new OutSimWheel(packetDataBytes);
        leftFront = new OutSimWheel(packetDataBytes);
        rightFront = new OutSimWheel(packetDataBytes);
    }

    /**
     * @return structure for left rear wheel
     */
    public OutSimWheel getLeftRear() {
        return leftRear;
    }

    /**
     * @return structure for right rear wheel
     */
    public OutSimWheel getRightRear() {
        return rightRear;
    }

    /**
     * @return structure for left front wheel
     */
    public OutSimWheel getLeftFront() {
        return leftFront;
    }

    /**
     * @return structure for right front wheel
     */
    public OutSimWheel getRightFront() {
        return rightFront;
    }
}
