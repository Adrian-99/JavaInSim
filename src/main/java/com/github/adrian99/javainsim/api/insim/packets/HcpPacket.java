/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.common.enums.DefaultCar;
import com.github.adrian99.javainsim.api.insim.packets.structures.CarHandicaps;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Array;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Structure;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;
import com.github.adrian99.javainsim.internal.insim.packets.base.InstructionPacket;
import com.github.adrian99.javainsim.internal.common.enums.EnumHelpers;
import com.github.adrian99.javainsim.internal.insim.packets.exceptions.PacketValidationException;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketBuilder;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketValidator;

import java.util.Map;

/**
 * HandiCaPs. You can send this packet to add mass and restrict the intake on each car model.
 * The same restriction applies to all drivers using a particular car model.
 * This can be useful for creating multi class hosts.
 */
public class HcpPacket extends AbstractPacket implements InstructionPacket {
    @Structure
    @Array(length = 32)
    private final CarHandicaps[] info;

    /**
     * Creates handicaps packet.
     * @param info map containing handicaps for {@link DefaultCar default LFS cars}.
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public HcpPacket(Map<DefaultCar, CarHandicaps> info) throws PacketValidationException {
        super(68, PacketType.HCP, 0);
        this.info = new CarHandicaps[32];
        for (var car : EnumHelpers.get(DefaultCar.class).getAllValuesCached()) {
            if (info.containsKey(car)) {
                this.info[car.ordinal()] = info.get(car);
            }
        }
        PacketValidator.validate(this);
    }

    @Override
    public byte[] getBytes() {
        return new PacketBuilder(size, type, reqI)
                .writeZeroByte()
                .writeStructureArray(info, 2)
                .getBytes();
    }
}
