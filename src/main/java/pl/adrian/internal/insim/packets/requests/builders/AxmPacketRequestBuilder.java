/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.internal.insim.packets.requests.builders;

import pl.adrian.api.insim.InSimConnection;
import pl.adrian.api.insim.packets.AxmPacket;
import pl.adrian.api.insim.packets.enums.TinySubtype;

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
    public BasicTinyPacketRequestBuilder<AxmPacket> forAllLayoutObjects() {
        return new BasicTinyPacketRequestBuilder<>(inSimConnection, TinySubtype.AXM);
    }

    /**
     * Option to choose if {@link AxmPacket} should contain information about specified connection's layout editor selection
     * @param ucid unique connection id (0 = local / non-zero = guest)
     * @return packet request builder
     */
    public TtcPacketRequestBuilder forConnectionLayoutEditorSelection(int ucid) {
        return new TtcPacketRequestBuilder(inSimConnection, ucid);
    }
}
