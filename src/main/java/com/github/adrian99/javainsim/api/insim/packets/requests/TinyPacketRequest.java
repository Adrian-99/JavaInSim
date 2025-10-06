/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.requests;

import com.github.adrian99.javainsim.api.insim.PacketListener;
import com.github.adrian99.javainsim.api.insim.packets.subtypes.tiny.TinyRequestingSubtype;
import com.github.adrian99.javainsim.internal.insim.packets.base.InstructionPacket;
import com.github.adrian99.javainsim.internal.insim.packets.requests.AbstractPacketRequest;
import org.slf4j.LoggerFactory;
import com.github.adrian99.javainsim.api.insim.packets.TinyPacket;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.internal.insim.packets.base.RequestablePacket;

/**
 * Packet request where {@link TinyPacket} serves as a request packet.
 */
public class TinyPacketRequest<T extends RequestablePacket> extends AbstractPacketRequest<T> {
    private final TinyRequestingSubtype<T> tinySubtype;

    /**
     * Creates packet request.
     * @param tinySubtype subtype to be used in request packet
     * @param callback method to be called when requested packet is received
     * @param timeoutMillis period of time (in milliseconds) after which packet request should be considered timed out
     */
    public TinyPacketRequest(TinyRequestingSubtype<T> tinySubtype, PacketListener<T> callback, long timeoutMillis) {
        super(
                LoggerFactory.getLogger(TinyPacketRequest.class),
                PacketType.fromPacketClass(tinySubtype.getRequestingPacketClass()),
                tinySubtype.isSinglePacketResponse(),
                callback,
                timeoutMillis
        );
        this.tinySubtype = tinySubtype;
    }

    @Override
    protected InstructionPacket createRequestPacket(short reqI) {
        return new TinyPacket(reqI, tinySubtype);
    }
}
