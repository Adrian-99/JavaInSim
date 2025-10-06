/*
 * Copyright (c) 2025, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.subtypes.ttc;

import com.github.adrian99.javainsim.api.insim.packets.TtcPacket;
import com.github.adrian99.javainsim.internal.insim.packets.base.RequestablePacket;
import com.github.adrian99.javainsim.internal.insim.packets.subtypes.PacketRequestingSubtype;

/**
 * Subtype of {@link TtcPacket} that serve as a request for other packet.
 * @param <T> class of the packet that is requested by the subtype
 */
public class TtcRequestingSubtype<T extends RequestablePacket>
        extends TtcSubtype
        implements PacketRequestingSubtype<T> {

    private final Class<T> requestingPacketClass;
    private final boolean singlePacketResponse;

    TtcRequestingSubtype(int value, Class<T> requestingPacketClass, boolean singlePacketResponse) {
        super(value);
        this.requestingPacketClass = requestingPacketClass;
        this.singlePacketResponse = singlePacketResponse;
    }

    @Override
    public Class<T> getRequestingPacketClass() {
        return requestingPacketClass;
    }

    @Override
    public boolean isSinglePacketResponse() {
        return singlePacketResponse;
    }
}
