/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.insim.packets.structures;

import pl.adrian.internal.insim.packets.annotations.Byte;
import pl.adrian.internal.insim.packets.structures.base.ComplexInstructionStructure;
import pl.adrian.internal.insim.packets.util.PacketBuilder;

/**
 * Car handicaps in 2 bytes - there is an array of these in the {@link pl.adrian.api.insim.packets.HcpPacket HcpPacket}.
 */
public class CarHandicaps implements ComplexInstructionStructure {
    @Byte(maxValue = 200)
    private final short hMass;
    @Byte(maxValue = 50)
    private final short hTRes;

    /**
     * Creates car handicaps.
     * @param hMass added mass (kg) - from 0 to 200
     * @param hTRes intake restriction (%) - from 0 to 50
     */
    public CarHandicaps(int hMass, int hTRes) {
        this.hMass = (short) hMass;
        this.hTRes = (short) hTRes;
    }

    @Override
    public void appendBytes(PacketBuilder packetBuilder) {
        packetBuilder.writeByte(hMass)
                .writeByte(hTRes);
    }
}
