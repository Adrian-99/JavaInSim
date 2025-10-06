/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.api.insim.packets.enums.ObjectType;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.insim.packets.flags.IsiFlag;
import com.github.adrian99.javainsim.api.common.flags.Flags;
import com.github.adrian99.javainsim.api.insim.packets.flags.ObhFlag;
import com.github.adrian99.javainsim.api.insim.packets.structures.CarContOBJ;
import com.github.adrian99.javainsim.api.insim.packets.subtypes.tiny.TinySubtypes;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Byte;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Short;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Structure;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Word;
import com.github.adrian99.javainsim.internal.insim.packets.base.InfoPacket;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;
import com.github.adrian99.javainsim.internal.common.util.PacketDataBytes;

/**
 * OBject Hit - car hit an autocross object or an unknown object.
 * To receive this packet set the {@link IsiFlag#OBH Isi OBH} flag in the {@link IsiPacket}.
 */
public class ObhPacket extends AbstractPacket implements InfoPacket {
    @Byte
    private final short plid;
    @Word
    private final int spClose;
    @Word
    private final int time;
    @Structure
    private final CarContOBJ c;
    @Short
    private final short x;
    @Short
    private final short y;
    @Byte
    private final short zByte;
    @Byte
    private final ObjectType index;
    @Byte
    private final Flags<ObhFlag> obhFlags;

    /**
     * Creates object hit packet. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public ObhPacket(PacketDataBytes packetDataBytes) {
        super(24, PacketType.OBH, 0);
        plid = packetDataBytes.readByte();
        spClose = packetDataBytes.readWord();
        time = packetDataBytes.readWord();
        c = new CarContOBJ(packetDataBytes);
        x = packetDataBytes.readShort();
        y = packetDataBytes.readShort();
        zByte = packetDataBytes.readByte();
        packetDataBytes.skipZeroByte();
        index = ObjectType.fromOrdinal(packetDataBytes.readByte());
        obhFlags = new Flags<>(ObhFlag.class, packetDataBytes.readByte());
    }

    /**
     * @return player's unique id
     */
    public short getPlid() {
        return plid;
    }

    /**
     * @return closing speed (10 = 1 m/s)
     */
    public short getClose() {
        return (short) (spClose & 0xFFF);
    }

    /**
     * @return looping time stamp (hundredths - time since reset - like {@link TinySubtypes#GTH Tiny GTH})
     */
    public int getTime() {
        return time;
    }

    /**
     * @return car in contact with object information
     */
    public CarContOBJ getC() {
        return c;
    }

    /**
     * @return X position (1 metre = 16)
     */
    public short getX() {
        return x;
    }

    /**
     * @return Y position (1 metre = 16)
     */
    public short getY() {
        return y;
    }

    /**
     * @return if {@link ObhFlag#LAYOUT} is set: height (1m = 4)
     */
    public short getZByte() {
        return zByte;
    }

    /**
     * @return object index
     */
    public ObjectType getIndex() {
        return index;
    }

    /**
     * @return OBH flags
     */
    public Flags<ObhFlag> getObhFlags() {
        return obhFlags;
    }
}
