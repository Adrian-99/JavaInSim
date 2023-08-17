/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.testutil;

import com.github.adrian99.javainsim.api.insim.packets.IsiPacket;
import com.github.adrian99.javainsim.api.insim.InSimConnection;

import java.io.IOException;

public class TestInSimConnection extends InSimConnection {
    public TestInSimConnection(String hostname, int port, IsiPacket initializationPacket) throws IOException {
        super(hostname, port, initializationPacket, 450);
    }
}
