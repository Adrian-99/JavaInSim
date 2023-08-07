/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.common.structures;

import pl.adrian.internal.common.util.PacketDataBytes;
import pl.adrian.internal.insim.packets.annotations.Int;
import pl.adrian.internal.insim.packets.structures.base.ComplexInstructionStructure;
import pl.adrian.internal.insim.packets.util.PacketBuilder;

/**
 * Structure containing 3 integers fixed point (1m = 65536).
 */
public class Vec implements ComplexInstructionStructure {
    @Int
    private final int x;
    @Int
    private final int y;
    @Int
    private final int z;

    /**
     * Creates vec structure. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public Vec(PacketDataBytes packetDataBytes) {
        this.x = packetDataBytes.readInt();
        this.y = packetDataBytes.readInt();
        this.z = packetDataBytes.readInt();
    }

    /**
     * Creates vec structure.
     * @param x X - right (1m = 65536)
     * @param y Y - forward (1m = 65536)
     * @param z Z - up (1m = 65536)
     */
    public Vec(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * @return X - right (1m = 65536)
     */
    public int getX() {
        return x;
    }

    /**
     * @return Y - forward (1m = 65536)
     */
    public int getY() {
        return y;
    }

    /**
     * @return Z - up (1m = 65536)
     */
    public int getZ() {
        return z;
    }

    @Override
    public void appendBytes(PacketBuilder packetBuilder) {
        packetBuilder.writeInt(x);
        packetBuilder.writeInt(y);
        packetBuilder.writeInt(z);
    }
}
