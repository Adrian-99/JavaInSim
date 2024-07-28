/*
 * Copyright (c) 2024, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.structures;

import com.github.adrian99.javainsim.api.common.flags.Flags;
import com.github.adrian99.javainsim.api.insim.packets.PlhPacket;
import com.github.adrian99.javainsim.api.insim.packets.flags.PlayerHandicapsFlag;
import com.github.adrian99.javainsim.internal.common.util.PacketDataBytes;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Byte;
import com.github.adrian99.javainsim.internal.insim.packets.structures.base.ComplexInstructionStructure;
import com.github.adrian99.javainsim.internal.insim.packets.util.PacketBuilder;

import java.util.HashSet;

/**
 * Player handicaps in 4 bytes - there is an array of these in the {@link PlhPacket}
 */
public class PlayerHandicaps implements ComplexInstructionStructure {
    @Byte
    private final short plid;
    @Byte
    private final Flags<PlayerHandicapsFlag> flags;
    @Byte(maxValue = 200)
    private final short hMass;
    @Byte(maxValue = 50)
    private final short hTRes;

    /**
     * Creates player handicaps.
     * @param plid player's unique id
     * @param silent player handicaps flags
     * @param hMass added mass (kg) - from 0 to 200, or {@code null} to leave unmodified
     * @param hTRes intake restriction (%) - from 0 to 50, or {@code null} to leave unmodified
     */
    public PlayerHandicaps(int plid, boolean silent, Integer hMass, Integer hTRes) {
        this.plid = (short) plid;
        var flagsBuilder = new HashSet<PlayerHandicapsFlag>();
        if (silent) {
            flagsBuilder.add(PlayerHandicapsFlag.SILENT);
        }
        if (hMass != null) {
            flagsBuilder.add(PlayerHandicapsFlag.SET_MASS);
            this.hMass = hMass.shortValue();
        } else {
            this.hMass = 0;
        }
        if (hTRes != null) {
            flagsBuilder.add(PlayerHandicapsFlag.SET_TRES);
            this.hTRes = hTRes.shortValue();
        } else {
            this.hTRes = 0;
        }
        this.flags = new Flags<>(flagsBuilder.toArray(new PlayerHandicapsFlag[0]));
    }

    /**
     * Creates player handicaps. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public PlayerHandicaps(PacketDataBytes packetDataBytes) {
        plid = packetDataBytes.readByte();
        flags = new Flags<>(PlayerHandicapsFlag.class, packetDataBytes.readByte());
        hMass = packetDataBytes.readByte();
        hTRes = packetDataBytes.readByte();
    }

    /**
     * @return player's unique id
     */
    public short getPlid() {
        return plid;
    }

    /**
     * @return player handicaps flags
     */
    public Flags<PlayerHandicapsFlag> getFlags() {
        return flags;
    }

    /**
     * @return added mass (kg) - from 0 to 200
     */
    public short getHMass() {
        return hMass;
    }

    /**
     * @return intake restriction (%) - from 0 to 50
     */
    public short getHTRes() {
        return hTRes;
    }

    @Override
    public void appendBytes(PacketBuilder packetBuilder) {
        packetBuilder.writeByte(plid)
                .writeByte(flags.getByteValue())
                .writeByte(hMass)
                .writeByte(hTRes);
    }
}
