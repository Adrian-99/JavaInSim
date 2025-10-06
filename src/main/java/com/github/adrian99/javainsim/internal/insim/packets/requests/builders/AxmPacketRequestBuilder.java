/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.internal.insim.packets.requests.builders;

import com.github.adrian99.javainsim.api.insim.InSimConnection;
import com.github.adrian99.javainsim.api.insim.packets.AxmPacket;
import com.github.adrian99.javainsim.api.insim.packets.subtypes.tiny.TinySubtypes;
import com.github.adrian99.javainsim.api.insim.packets.subtypes.ttc.TtcSubtypes;

/**
 * Builder for {@link AxmPacket} request.
 */
public class AxmPacketRequestBuilder {
    private final InSimConnection inSimConnection;

    /**
     * Creates {@link AxmPacket} request.
     * @param inSimConnection InSim connection to request packet from
     */
    public AxmPacketRequestBuilder(InSimConnection inSimConnection) {
        this.inSimConnection = inSimConnection;
    }

    /**
     * Option to choose if {@link AxmPacket} should contain information about all layout objects.
     * @return packet request builder
     */
    public TinyPacketRequestBuilder<AxmPacket> forAllLayoutObjects() {
        return new TinyPacketRequestBuilder<>(inSimConnection, TinySubtypes.AXM);
    }

    /**
     * Option to choose if {@link AxmPacket} should contain information about specified connection's layout editor selection
     * @param ucid unique connection id (0 = local / non-zero = guest)
     * @return packet request builder
     */
    public TtcPacketRequestBuilder<AxmPacket> forConnectionLayoutEditorSelection(int ucid) {
        return new TtcPacketRequestBuilder<>(inSimConnection, TtcSubtypes.SEL, ucid, 0, 0, 0);
    }
}
