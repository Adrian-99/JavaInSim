/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.insim.packets;

import pl.adrian.api.insim.InSimConnection;
import pl.adrian.api.insim.packets.enums.*;
import pl.adrian.api.common.flags.Flags;
import pl.adrian.api.insim.packets.flags.StaFlag;
import pl.adrian.internal.insim.packets.annotations.*;
import pl.adrian.internal.insim.packets.annotations.Byte;
import pl.adrian.internal.insim.packets.annotations.Float;
import pl.adrian.api.insim.packets.structures.RaceLaps;
import pl.adrian.internal.insim.packets.base.AbstractPacket;
import pl.adrian.internal.insim.packets.base.RequestablePacket;
import pl.adrian.internal.common.util.PacketDataBytes;
import pl.adrian.internal.insim.packets.requests.builders.SingleTinyPacketRequestBuilder;

/**
 * STAte. LFS will send this packet any time the info in it changes.
 */
public class StaPacket extends AbstractPacket implements RequestablePacket {
    @Float
    private final float replaySpeed;
    @Word
    private final Flags<StaFlag> flags;
    @Byte
    private final ViewIdentifier inGameCam;
    @Byte
    private final short viewPlid;
    @Byte
    private final short numP;
    @Byte
    private final short numConns;
    @Byte
    private final short numFinished;
    @Byte
    private final RaceProgress raceInProg;
    @Byte
    private final short qualMins;
    @Byte
    private final RaceLaps raceLaps;
    @Byte
    private final ServerStatus serverStatus;
    @Char
    @Array(length = 6)
    private final String track;
    @Byte
    private final short weather;
    @Byte
    private final Wind wind;

    /**
     * Creates state packet. Constructor used only internally.
     * @param reqI 0 unless this is a reply to an {@link pl.adrian.api.insim.packets.enums.TinySubtype#SST Tiny SST} request
     * @param packetDataBytes packet data bytes
     */
    public StaPacket(short reqI, PacketDataBytes packetDataBytes) {
        super(28, PacketType.STA, reqI);
        packetDataBytes.skipZeroByte();
        replaySpeed = packetDataBytes.readFloat();
        flags = new Flags<>(StaFlag.class, packetDataBytes.readWord());
        inGameCam = ViewIdentifier.fromOrdinal(packetDataBytes.readByte());
        viewPlid = packetDataBytes.readByte();
        numP = packetDataBytes.readByte();
        numConns = packetDataBytes.readByte();
        numFinished = packetDataBytes.readByte();
        raceInProg = RaceProgress.fromOrdinal(packetDataBytes.readByte());
        qualMins = packetDataBytes.readByte();
        raceLaps = new RaceLaps(packetDataBytes);
        packetDataBytes.skipZeroByte();
        serverStatus = ServerStatus.fromOrdinal(packetDataBytes.readByte());
        track = packetDataBytes.readCharArray(6);
        weather = packetDataBytes.readByte();
        wind = Wind.fromOrdinal(packetDataBytes.readByte());
    }

    /**
     * @return replay speed - 1.0 is normal speed
     */
    public float getReplaySpeed() {
        return replaySpeed;
    }

    /**
     * @return state flags
     */
    public Flags<StaFlag> getFlags() {
        return flags;
    }

    /**
     * @return which type of camera is selected
     */
    public ViewIdentifier getInGameCam() {
        return inGameCam;
    }

    /**
     * @return unique ID of viewed player (0 = none)
     */
    public short getViewPlid() {
        return viewPlid;
    }

    /**
     * @return number of players in race
     */
    public short getNumP() {
        return numP;
    }

    /**
     * @return number of connections including host
     */
    public short getNumConns() {
        return numConns;
    }

    /**
     * @return number finished or qualified
     */
    public short getNumFinished() {
        return numFinished;
    }

    /**
     * @return race progress
     */
    public RaceProgress getRaceInProg() {
        return raceInProg;
    }

    /**
     * @return number of minutes of qualifications
     */
    public short getQualMins() {
        return qualMins;
    }

    /**
     * @return number of race laps (or hours)
     */
    public RaceLaps getRaceLaps() {
        return raceLaps;
    }

    /**
     * @return server status
     */
    public ServerStatus getServerStatus() {
        return serverStatus;
    }

    /**
     * @return short name for track e.g. FE2R
     */
    public String getTrack() {
        return track;
    }

    /**
     * @return weather - 0,1,2...
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
     * Creates builder for packet request for {@link StaPacket}.
     * @param inSimConnection InSim connection to request packet from
     * @return packet request builder
     */
    public static SingleTinyPacketRequestBuilder<StaPacket> request(InSimConnection inSimConnection) {
        return new SingleTinyPacketRequestBuilder<>(inSimConnection, TinySubtype.SST);
    }
}
