/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.internal.common.util.PacketDataBytes;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Unsigned;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Word;
import com.github.adrian99.javainsim.internal.insim.packets.base.InfoPacket;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.insim.packets.flags.ConfirmationFlag;
import com.github.adrian99.javainsim.api.common.flags.Flags;
import com.github.adrian99.javainsim.api.insim.packets.flags.PlayerFlag;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Byte;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;

/**
 * FINished race notification. The packet is sent by LFS when any player finishes race
 * (not a final result - use {@link ResPacket}).
 */
public class FinPacket extends AbstractPacket implements InfoPacket {
    @Byte
    private final short plid;
    @Unsigned
    private final long tTime;
    @Unsigned
    private final long bTime;
    @Byte
    private final short numStops;
    @Byte
    private final Flags<ConfirmationFlag> confirm;
    @Word
    private final int lapsDone;
    @Word
    private final Flags<PlayerFlag> flags;

    /**
     * Creates finished race notification packet. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public FinPacket(PacketDataBytes packetDataBytes) {
        super(20, PacketType.FIN, 0);
        plid = packetDataBytes.readByte();
        tTime = packetDataBytes.readUnsigned();
        bTime = packetDataBytes.readUnsigned();
        packetDataBytes.skipZeroByte();
        numStops = packetDataBytes.readByte();
        confirm = new Flags<>(ConfirmationFlag.class, packetDataBytes.readByte());
        packetDataBytes.skipZeroByte();
        lapsDone = packetDataBytes.readWord();
        flags = new Flags<>(PlayerFlag.class, packetDataBytes.readWord());
    }

    /**
     * @return player's unique id (0 = player left before result was sent)
     */
    public short getPlid() {
        return plid;
    }

    /**
     * @return race time (ms)
     */
    public long getTTime() {
        return tTime;
    }

    /**
     * @return best lap (ms)
     */
    public long getBTime() {
        return bTime;
    }

    /**
     * @return number of pit stops
     */
    public short getNumStops() {
        return numStops;
    }

    /**
     * @return confirmation flags
     */
    public Flags<ConfirmationFlag> getConfirm() {
        return confirm;
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
}
