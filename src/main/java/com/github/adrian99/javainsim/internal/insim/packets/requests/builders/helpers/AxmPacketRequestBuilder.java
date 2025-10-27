/*
 * Copyright (c) 2025, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.internal.insim.packets.requests.builders.helpers;

import com.github.adrian99.javainsim.api.insim.InSimConnection;
import com.github.adrian99.javainsim.api.insim.packets.AxmPacket;
import com.github.adrian99.javainsim.api.insim.packets.TtcPacket;
import com.github.adrian99.javainsim.api.insim.packets.subtypes.tiny.TinySubtypes;
import com.github.adrian99.javainsim.api.insim.packets.subtypes.ttc.TtcSubtypes;
import com.github.adrian99.javainsim.internal.insim.packets.requests.builders.TtcPacketRequestBuilder;
import com.github.adrian99.javainsim.internal.insim.packets.requests.builders.RecurringTtcPacketRequestBuilder;
import com.github.adrian99.javainsim.internal.insim.packets.requests.builders.TinyPacketRequestBuilder;

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
    public ConnectionLayoutEditorSelection forConnectionLayoutEditorSelection(int ucid) {
        return new ConnectionLayoutEditorSelection(inSimConnection, ucid);
    }

    /**
     * Builder for {@link AxmPacket} request for connection layout editor selection.
     */
    public static class ConnectionLayoutEditorSelection extends TtcPacketRequestBuilder<AxmPacket> {
        private final int ucid;

        /**
         * Creates packet request builder.
         * @param inSimConnection InSim connection to request packet from
         * @param ucid            unique connection id (0 = local / non-zero = guest)
         */
        public ConnectionLayoutEditorSelection(InSimConnection inSimConnection, int ucid) {
            super(inSimConnection, TtcSubtypes.SEL, ucid, 0, 0, 0);
            this.ucid = ucid;
        }

        /**
         * Option to choose if {@link AxmPacket} should be automatically sent every time layout editor selection changes
         * @return packet request builder
         */
        public RecurringTtcPacketRequestBuilder<AxmPacket> forEverySelectionChange() {
            return new RecurringTtcPacketRequestBuilder<>(
                    inSimConnection,
                    TtcSubtypes.SEL_START,
                    ucid,
                    0,
                    0,
                    0,
                    reqI -> new TtcPacket(TtcSubtypes.SEL_STOP, ucid, 0, 0, 0, reqI)
            );
        }
    }
}
