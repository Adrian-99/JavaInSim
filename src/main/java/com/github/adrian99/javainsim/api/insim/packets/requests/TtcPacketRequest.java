/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.requests;

import com.github.adrian99.javainsim.api.insim.PacketListener;
import com.github.adrian99.javainsim.api.insim.packets.AxmPacket;
import com.github.adrian99.javainsim.api.insim.packets.enums.TtcSubtype;
import com.github.adrian99.javainsim.internal.insim.packets.base.InstructionPacket;
import com.github.adrian99.javainsim.internal.insim.packets.requests.AbstractPacketRequest;
import org.slf4j.LoggerFactory;
import com.github.adrian99.javainsim.api.insim.packets.TtcPacket;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;

/**
 * Packet request where {@link TtcPacket} serves as a request packet. Currently only {@link AxmPacket}
 *  * can be requested this way.
 */
public class TtcPacketRequest extends AbstractPacketRequest<AxmPacket> {
    private final int ucid;
    /**
     * Creates packet request.
     * @param ucid unique connection id (0 = local / non-zero = guest)
     * @param callback method to be called when requested packet is received
     * @param timeoutMillis period of time (in milliseconds) after which packet request should be considered timed out
     */
    public TtcPacketRequest(int ucid, PacketListener<AxmPacket> callback, long timeoutMillis) {
        super(
                LoggerFactory.getLogger(TtcPacketRequest.class),
                PacketType.AXM,
                false,
                callback,
                timeoutMillis
        );
        this.ucid = ucid;
    }

    @Override
    protected InstructionPacket createRequestPacket(short reqI) {
        return new TtcPacket(TtcSubtype.SEL, ucid, 0, 0, 0, reqI);
    }
}
