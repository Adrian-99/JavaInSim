/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim;

import com.github.adrian99.javainsim.internal.insim.packets.base.InfoPacket;

/**
 * This interface allows to create packet listener - the function that will be called each time
 * packet of specified type will be received from LFS. Packet listener can be registered and unregistered
 * using {@link InSimConnection#listen(Class, PacketListener) listen} and
 * {@link InSimConnection#stopListening(Class, PacketListener) stopListening} methods respectively.
 * @param <T> type of the packet that will be handled by this packet listener
 */
@FunctionalInterface
public interface PacketListener<T extends InfoPacket> {
    /**
     * Method that will be called each time packet of specified type will be received from LFS.
     * @param inSimConnection InSim connection that triggered the listener
     * @param packet received packet
     */
    void onPacketReceived(InSimConnection inSimConnection, T packet);
}
