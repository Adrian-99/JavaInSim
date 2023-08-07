/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.outsim.structures;

import pl.adrian.internal.common.util.PacketDataBytes;

/**
 * Structure for header.
 */
public class OutSimHeader {
    /**
     * Size of the structure.
     */
    public static final int SIZE = 4;

    private final byte l;
    private final byte f;
    private final byte s;
    private final byte t;

    /**
     * Creates structure for header. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public OutSimHeader(PacketDataBytes packetDataBytes) {
        l = packetDataBytes.readChar();
        f = packetDataBytes.readChar();
        s = packetDataBytes.readChar();
        t = packetDataBytes.readChar();
    }

    /**
     * @return L
     */
    public byte getL() {
        return l;
    }

    /**
     * @return F
     */
    public byte getF() {
        return f;
    }

    /**
     * @return S
     */
    public byte getS() {
        return s;
    }

    /**
     * @return T
     */
    public byte getT() {
        return t;
    }
}
