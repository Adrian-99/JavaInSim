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
 * Structure for ID.
 */
public class OutSimId {
    /**
     * Size of the structure.
     */
    public static final int SIZE = 4;

    private final int id;

    /**
     * Creates structure for ID. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public OutSimId(PacketDataBytes packetDataBytes) {
        id = packetDataBytes.readInt();
    }

    /**
     * @return OutSim ID from cfg.txt
     */
    public int getId() {
        return id;
    }
}
