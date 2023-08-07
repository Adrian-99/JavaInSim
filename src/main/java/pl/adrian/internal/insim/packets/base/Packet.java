/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.internal.insim.packets.base;

import pl.adrian.api.insim.packets.enums.PacketType;

/**
 * Interface that should be implemented by all classes representing LFS packets.
 */
public interface Packet {
    /**
     * @return total packet size - a multiple of 4
     */
    short getSize();

    /**
     * @return packet identifier
     */
    PacketType getType();

    /**
     * @return non-zero if the packet is a packet request or a reply to a request
     */
    short getReqI();
}
