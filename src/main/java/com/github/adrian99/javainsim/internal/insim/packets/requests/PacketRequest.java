/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.internal.insim.packets.requests;

import com.github.adrian99.javainsim.internal.insim.packets.base.InfoPacket;
import com.github.adrian99.javainsim.internal.insim.packets.base.InstructionPacket;
import com.github.adrian99.javainsim.api.insim.InSimConnection;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;

/**
 * This interface should be implemented by all packet request implementations.
 */
public interface PacketRequest {
    /**
     * Assigns free reqI value to the packet request.
     * @param reqI reqI value
     */
    void assignReqI(short reqI);

    /**
     * @return type of the requested packet
     */
    PacketType getRequestedPacketType();

    /**
     * @return {@link InstructionPacket} that serves as a request
     */
    InstructionPacket getRequestPacket();

    /**
     * Tries to handle received {@link InfoPacket}.
     * @param inSimConnection InSim connection that packet was received from
     * @param receivedPacket received packet
     * @return whether packet request should now be removed (expected single packet response and received packet matched)
     */
    boolean handleReceivedPacket(InSimConnection inSimConnection, InfoPacket receivedPacket);

    /**
     * Checks whether provided packet type and reqI value match with information stored in this packet request.
     * @param packetType packet type
     * @param reqI reqI value
     * @return whether is matching
     */
    boolean matches(PacketType packetType, short reqI);

    /**
     * @return whether packet request is timed out
     */
    boolean isTimedOut();
}
