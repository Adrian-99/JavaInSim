/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets;

import com.github.adrian99.javainsim.api.common.flags.Flags;
import com.github.adrian99.javainsim.api.insim.InSimConnection;
import com.github.adrian99.javainsim.api.insim.packets.enums.PacketType;
import com.github.adrian99.javainsim.api.insim.packets.enums.TinySubtype;
import com.github.adrian99.javainsim.api.insim.packets.enums.Wind;
import com.github.adrian99.javainsim.api.insim.packets.flags.RaceFlag;
import com.github.adrian99.javainsim.api.insim.packets.structures.LapTiming;
import com.github.adrian99.javainsim.api.insim.packets.structures.RaceLaps;
import com.github.adrian99.javainsim.internal.common.util.PacketDataBytes;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Array;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Byte;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Char;
import com.github.adrian99.javainsim.internal.insim.packets.annotations.Word;
import com.github.adrian99.javainsim.internal.insim.packets.base.AbstractPacket;
import com.github.adrian99.javainsim.internal.insim.packets.requests.builders.SingleTinyPacketRequestBuilder;
import com.github.adrian99.javainsim.internal.insim.packets.base.RequestablePacket;

/**
 * Race STart. The packet is sent by LFS when race starts.
 */
public class RstPacket extends AbstractPacket implements RequestablePacket {
    @Byte
    private final RaceLaps raceLaps;
    @Byte
    private final short qualMins;
    @Byte
    private final short numP;
    @Byte
    private final LapTiming timing;
    @Char
    @Array(length = 6)
    private final String track;
    @Byte
    private final short weather;
    @Byte
    private final Wind wind;
    @Word
    private final Flags<RaceFlag> flags;
    @Word
    private final int numNodes;
    @Word
    private final int finish;
    @Word
    private final int split1;
    @Word
    private final int split2;
    @Word
    private final int split3;

    /**
     * Creates race start packet. Constructor used only internally.
     * @param reqI 0 unless this is a reply to an {@link TinySubtype#RST Tiny RST} request
     * @param packetDataBytes packet data bytes
     */
    public RstPacket(short reqI, PacketDataBytes packetDataBytes) {
        super(28, PacketType.RST, reqI);
        packetDataBytes.skipZeroByte();
        raceLaps = new RaceLaps(packetDataBytes);
        qualMins = packetDataBytes.readByte();
        numP = packetDataBytes.readByte();
        timing = new LapTiming(packetDataBytes);
        track = packetDataBytes.readCharArray(6);
        weather = packetDataBytes.readByte();
        wind = Wind.fromOrdinal(packetDataBytes.readByte());
        flags = new Flags<>(RaceFlag.class, packetDataBytes.readWord());
        numNodes = packetDataBytes.readWord();
        finish = packetDataBytes.readWord();
        split1 = packetDataBytes.readWord();
        split2 = packetDataBytes.readWord();
        split3 = packetDataBytes.readWord();
    }

    /**
     * @return race laps
     */
    public RaceLaps getRaceLaps() {
        return raceLaps;
    }

    /**
     * @return qualifications minutes, 0 if race
     */
    public short getQualMins() {
        return qualMins;
    }

    /**
     * @return number of players in race
     */
    public short getNumP() {
        return numP;
    }

    /**
     * @return lap timing
     */
    public LapTiming getTiming() {
        return timing;
    }

    /**
     * @return short track name
     */
    public String getTrack() {
        return track;
    }

    /**
     * @return weather
     */
    public short getWeather() {
        return weather;
    }

    /**
     * @return wind type
     */
    public Wind getWind() {
        return wind;
    }

    /**
     * @return race flags
     */
    public Flags<RaceFlag> getFlags() {
        return flags;
    }

    /**
     * @return total number of nodes in the path
     */
    public int getNumNodes() {
        return numNodes;
    }

    /**
     * @return node index - finish line
     */
    public int getFinish() {
        return finish;
    }

    /**
     * @return node index - split 1
     */
    public int getSplit1() {
        return split1;
    }

    /**
     * @return node index - split 2
     */
    public int getSplit2() {
        return split2;
    }

    /**
     * @return node index - split 3
     */
    public int getSplit3() {
        return split3;
    }

    /**
     * Creates builder for packet request for {@link RstPacket}.
     * @param inSimConnection InSim connection to request packet from
     * @return packet request builder
     */
    public static SingleTinyPacketRequestBuilder<RstPacket> request(InSimConnection inSimConnection) {
        return new SingleTinyPacketRequestBuilder<>(inSimConnection, TinySubtype.RST);
    }
}
