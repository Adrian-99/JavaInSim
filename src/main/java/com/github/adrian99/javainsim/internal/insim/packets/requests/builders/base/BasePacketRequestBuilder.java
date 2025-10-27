/*
 * Copyright (c) 2025, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.internal.insim.packets.requests.builders.base;

import com.github.adrian99.javainsim.api.insim.InSimConnection;

/**
 * Basic builder of packet request. Both single and multiple packet
 * responses are allowed.
 */
public abstract class BasePacketRequestBuilder {
    /**
     * InSim connection to request packet from
     */
    protected final InSimConnection inSimConnection;
    /**
     * timeout (in milliseconds) of the packet request - can be overridden by inheritors
     */
    protected long requestTimeoutMillis = 5000;

    /**
     * Creates basic builder of packet request.
     * @param inSimConnection InSim connection to request packet from
     */
    protected BasePacketRequestBuilder(InSimConnection inSimConnection) {
        this.inSimConnection = inSimConnection;
    }
}
