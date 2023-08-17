/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.insim.packets.enums.SmallSubtype;
import com.github.adrian99.javainsim.api.common.enums.DefaultCar;
import com.github.adrian99.javainsim.api.common.flags.Flags;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Byte;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Unsigned;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;
import com.github.adrian99.javainsim.internal.insim.packets.base.InstructionPacket;
import com.github.adrian99.javainsim.internal.insim.packets.exceptions.PacketValidationException;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketBuilder;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketValidator;

/**
 * PLayer Cars. Allows limiting the cars that can be used by a given connection.
 * The resulting set of selectable cars is a subset of the cars set to be available
 * on the host (by the /cars command or {@link SmallSubtype#ALC Small ALC}).
 */
public class PlcPacket extends AbstractPacket implements InstructionPacket {
    @Byte
    private final short ucid;
    @Unsigned
    private final Flags<DefaultCar> cars;

    /**
     * Creates player cars packet.
     * @param ucid connection's unique id (0 = host / 255 = all)
     * @param cars allowed cars
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public PlcPacket(int ucid, Flags<DefaultCar> cars) throws PacketValidationException {
        super(12, PacketType.PLC, 0);
        this.ucid = (short) ucid;
        this.cars = cars;
        PacketValidator.validate(this);
    }

    @Override
    public byte[] getBytes() {
        return new PacketBuilder(size, type, reqI)
                .writeZeroByte()
                .writeByte(ucid)
                .writeZeroBytes(3)
                .writeUnsigned(cars.getUnsignedValue())
                .getBytes();
    }
}
