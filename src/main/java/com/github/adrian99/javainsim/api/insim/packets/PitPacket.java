/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.insim.packets.enums.PenaltyValue;
import com.github.adrian99.javainsim.api.common.flags.Flags;
import com.github.adrian99.javainsim.api.insim.packets.flags.PitWorkFlag;
import com.github.adrian99.javainsim.api.insim.packets.flags.PlayerFlag;
import com.github.adrian99.javainsim.api.insim.packets.structures.Tyres;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Array;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Byte;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Unsigned;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Word;
import com.github.adrian99.javainsim.internal.insim.packets.base.InfoPacket;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;
import com.github.adrian99.javainsim.internal.common.util.PacketDataBytes;

/**
 * PIT stop. The packet is sent by LFS when any player stops at pit garage.
 */
public class PitPacket extends AbstractPacket implements InfoPacket {
    @Byte
    private final short plid;
    @Word
    private final int lapsDone;
    @Word
    private final Flags<PlayerFlag> flags;
    @Byte
    private final short fuelAdd;
    @Byte
    private final PenaltyValue penalty;
    @Byte
    private final short numStops;
    @Byte
    @Array(length = 4)
    private final Tyres tyres;
    @Unsigned
    private final Flags<PitWorkFlag> work;

    /**
     * Creates pit stop packet. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public PitPacket(PacketDataBytes packetDataBytes) {
        super(24, PacketType.PIT, 0);
        plid = packetDataBytes.readByte();
        lapsDone = packetDataBytes.readWord();
        flags = new Flags<>(PlayerFlag.class, packetDataBytes.readWord());
        fuelAdd = packetDataBytes.readByte();
        penalty = PenaltyValue.fromOrdinal(packetDataBytes.readByte());
        numStops = packetDataBytes.readByte();
        packetDataBytes.skipZeroByte();
        tyres = new Tyres(packetDataBytes);
        work = new Flags<>(PitWorkFlag.class, packetDataBytes.readUnsigned());
        packetDataBytes.skipZeroBytes(4);
    }

    /**
     * @return player's unique id
     */
    public short getPlid() {
        return plid;
    }

    /**
     * @return laps completed
     */
    public int getLapsDone() {
        return lapsDone;
    }

    /**
     * @return player flags
     */
    public Flags<PlayerFlag> getFlags() {
        return flags;
    }

    /**
     * @return fuel added percent (if /showfuel yes, 255 otherwise)
     */
    public short getFuelAdd() {
        return fuelAdd;
    }

    /**
     * @return current penalty value
     */
    public PenaltyValue getPenalty() {
        return penalty;
    }

    /**
     * @return number of pit stops
     */
    public short getNumStops() {
        return numStops;
    }

    /**
     * @return tyres changed
     */
    public Tyres getTyres() {
        return tyres;
    }

    /**
     * @return pit work
     */
    public Flags<PitWorkFlag> getWork() {
        return work;
    }
}
