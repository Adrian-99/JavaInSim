/*
 * Copyright (c) 2025, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.internal.insim.packets.subtypes;

import com.github.adrian99.javainsim.internal.insim.packets.base.InstructionPacket;
import com.github.adrian99.javainsim.internal.insim.packets.base.RequestablePacket;

/**
 * Base interface for packet subtypes, which, when sent within {@link InstructionPacket},
 * trigger LFS to send {@link RequestablePacket} in response.
 * @param <T> type of the expected response packet
 */
public interface PacketRequestingSubtype<T extends RequestablePacket> {
    /**
     * @return class of the packet that is a response to packet of this subtype
     */
    Class<T> getRequestingPacketClass();

    /**
     * @return whether single packet is expected in response to request of this type
     */
    boolean isSinglePacketResponse();
}
