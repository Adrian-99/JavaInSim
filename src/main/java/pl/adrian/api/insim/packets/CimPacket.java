/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.insim.packets;

import pl.adrian.api.insim.packets.enums.*;
import pl.adrian.internal.insim.packets.annotations.Byte;
import pl.adrian.internal.insim.packets.base.InfoPacket;
import pl.adrian.internal.insim.packets.base.AbstractPacket;
import pl.adrian.internal.common.util.PacketDataBytes;

import java.util.Optional;

/**
 * Conn Interface Mode - connection's interface mode.
 */
public class CimPacket extends AbstractPacket implements InfoPacket {
    @Byte
    private final short ucid;
    @Byte
    private final InterfaceMode mode;
    @Byte
    private final short subMode;
    @Byte
    private final ObjectType selType;

    /**
     * Creates connection's interface mode packet. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public CimPacket(PacketDataBytes packetDataBytes) {
        super(8, PacketType.CIM, 0);
        ucid = packetDataBytes.readByte();
        mode = InterfaceMode.fromOrdinal(packetDataBytes.readByte());
        subMode = packetDataBytes.readByte();
        selType = ObjectType.fromOrdinal(packetDataBytes.readByte());
        packetDataBytes.skipZeroByte();
    }

    /**
     * @return connection's unique id (0 = local)
     */
    public short getUcid() {
        return ucid;
    }

    /**
     * @return interface mode
     */
    public InterfaceMode getMode() {
        return mode;
    }

    /**
     * @return normal interface submode, or empty if mode is not normal
     */
    public Optional<NormalInterfaceSubmode> getNormalSubmode() {
        if (mode.equals(InterfaceMode.NORMAL)) {
            return Optional.of(NormalInterfaceSubmode.fromOrdinal(subMode));
        } else {
            return Optional.empty();
        }
    }

    /**
     * @return garage interface submode, or empty if mode is not garage
     */
    public Optional<GarageInterfaceSubmode> getGarageSubmode() {
        if (mode.equals(InterfaceMode.GARAGE)) {
            return Optional.of(GarageInterfaceSubmode.fromOrdinal(subMode));
        } else {
            return Optional.empty();
        }
    }

    /**
     * @return shift U interface submode, or empty if mode is not shift U
     */
    public Optional<ShiftUInterfaceSubmode> getShiftUSubmode() {
        if (mode.equals(InterfaceMode.SHIFTU)) {
            return Optional.of(ShiftUInterfaceSubmode.fromOrdinal(subMode));
        } else {
            return Optional.empty();
        }
    }

    /**
     * @return selected object type
     */
    public ObjectType getSelType() {
        return selType;
    }
}
