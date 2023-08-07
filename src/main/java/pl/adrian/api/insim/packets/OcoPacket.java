/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.insim.packets;

import pl.adrian.api.insim.packets.enums.OcoAction;
import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.api.insim.packets.enums.StartLightsIndex;
import pl.adrian.api.common.flags.Flags;
import pl.adrian.api.insim.packets.flags.StartLightsDataFlag;
import pl.adrian.internal.insim.packets.annotations.Byte;
import pl.adrian.internal.insim.packets.base.InstructionPacket;
import pl.adrian.internal.insim.packets.base.AbstractPacket;
import pl.adrian.internal.insim.packets.exceptions.PacketValidationException;
import pl.adrian.internal.insim.packets.util.PacketBuilder;
import pl.adrian.internal.insim.packets.util.PacketValidator;

/**
 * Object COntrol. The packet is used for switching start lights.
 */
public class OcoPacket extends AbstractPacket implements InstructionPacket {
    @Byte
    private final OcoAction ocoAction;
    @Byte
    private final StartLightsIndex index;
    @Byte
    private final short identifier;
    @Byte
    private final Flags<StartLightsDataFlag> data;

    /**
     * Creates object control packet.
     * @param ocoAction requested action
     * @param index specifies which lights should be overridden
     * @param identifier identify particular start lights objects (0 to 63 or 255 = all)
     * @param data specifies particular bulbs
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public OcoPacket(OcoAction ocoAction,
                     StartLightsIndex index,
                     int identifier,
                     Flags<StartLightsDataFlag> data) throws PacketValidationException {
        super(8, PacketType.OCO, 0);
        this.ocoAction = ocoAction;
        this.index = index;
        this.identifier = (short) identifier;
        this.data = data;
        PacketValidator.validate(this);
    }

    @Override
    public byte[] getBytes() {
        return new PacketBuilder(size, type, reqI)
                .writeZeroByte()
                .writeByte(ocoAction.getValue())
                .writeByte(index.getValue())
                .writeByte(identifier)
                .writeByte(data.getByteValue())
                .getBytes();
    }
}
