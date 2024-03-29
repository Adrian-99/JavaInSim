/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.internal.common.util.PacketDataBytes;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Byte;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Unsigned;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Word;
import com.github.adrian99.javainsim.internal.insim.packets.base.InfoPacket;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.insim.packets.enums.PenaltyValue;
import com.github.adrian99.javainsim.api.common.flags.Flags;
import com.github.adrian99.javainsim.api.insim.packets.flags.PlayerFlag;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;

/**
 * LAP time. The packet is sent by LFS when any player finishes a lap.
 */
public class LapPacket extends AbstractPacket implements InfoPacket {
    @Byte
    private final short plid;
    @Unsigned
    private final long lTime;
    @Unsigned
    private final long eTime;
    @Word
    private final int lapsDone;
    @Word
    private final Flags<PlayerFlag> flags;
    @Byte
    private final PenaltyValue penalty;
    @Byte
    private final short numStops;
    @Byte
    private final short fuel200;

    /**
     * Creates lap time packet. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public LapPacket(PacketDataBytes packetDataBytes) {
        super(20, PacketType.LAP, 0);
        plid = packetDataBytes.readByte();
        lTime = packetDataBytes.readUnsigned();
        eTime = packetDataBytes.readUnsigned();
        lapsDone = packetDataBytes.readWord();
        flags = new Flags<>(PlayerFlag.class, packetDataBytes.readWord());
        packetDataBytes.skipZeroByte();
        penalty = PenaltyValue.fromOrdinal(packetDataBytes.readByte());
        numStops = packetDataBytes.readByte();
        fuel200 = packetDataBytes.readByte();
    }

    /**
     * @return player's unique id
     */
    public short getPlid() {
        return plid;
    }

    /**
     * @return lap time (ms)
     */
    public long getLTime() {
        return lTime;
    }

    /**
     * @return total time (ms)
     */
    public long getETime() {
        return eTime;
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
     * @return double fuel percent (if /showfuel yes, 255 otherwise)
     */
    public short getFuel200() {
        return fuel200;
    }
}
