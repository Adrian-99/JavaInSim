/*
 * Copyright (c) 2025, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.requests;

import com.github.adrian99.javainsim.api.insim.PacketListener;
import com.github.adrian99.javainsim.api.insim.packets.SmallPacket;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.insim.packets.subtypes.small.SmallRequestingSubtype;
import com.github.adrian99.javainsim.internal.insim.packets.base.InstructionPacket;
import com.github.adrian99.javainsim.internal.insim.packets.base.RequestablePacket;
import com.github.adrian99.javainsim.internal.insim.packets.requests.AbstractPacketRequest;
import org.slf4j.LoggerFactory;

/**
 * Packet request where {@link SmallPacket} serves as a request packet.
 */
public class SmallPacketRequest<T extends RequestablePacket> extends AbstractPacketRequest<T> {
    private final SmallRequestingSubtype<T> smallSubtype;
    private final long unsignedValue;

    /**
     * Creates packet request.
     * @param smallSubtype subtype to be used in request packet
     * @param unsignedValue additional value to be sent in request packet
     * @param callback method to be called when requested packet is received
     * @param timeoutMillis period of time (in milliseconds) after which packet request should be considered timed out
     */
    public SmallPacketRequest(SmallRequestingSubtype<T> smallSubtype,
                              long unsignedValue,
                              PacketListener<T> callback,
                              long timeoutMillis) {
        super(
                LoggerFactory.getLogger(SmallPacketRequest.class),
                PacketType.fromPacketClass(smallSubtype.getRequestingPacketClass()),
                smallSubtype.isSinglePacketResponse(),
                callback,
                timeoutMillis
        );
        this.smallSubtype = smallSubtype;
        this.unsignedValue = unsignedValue;
    }

    @Override
    protected InstructionPacket createRequestPacket(short reqI) {
        return new SmallPacket(reqI, smallSubtype, unsignedValue);
    }
}
