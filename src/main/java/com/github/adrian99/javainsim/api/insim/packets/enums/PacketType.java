/*
 * Copyright (c) 2024, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.enums;

import com.github.adrian99.javainsim.api.insim.packets.*;
import com.github.adrian99.javainsim.internal.common.enums.EnumHelpers;
import com.github.adrian99.javainsim.internal.insim.packets.base.Packet;

/**
 * Enumeration for packet types.
 */
public enum PacketType {
    /**
     * 0: not used
     */
    NONE(null),
    /**
     * 1 - instruction: insim initialise
     */
    ISI(IsiPacket.class),
    /**
     * 2 - info: version info
     */
    VER(VerPacket.class),
    /**
     * 3 - both ways: multi-purpose
     */
    TINY(TinyPacket.class),
    /**
     * 4 - both ways: multi-purpose
     */
    SMALL(SmallPacket.class),
    /**
     * 5 - info: state info
     */
    STA(StaPacket.class),
    /**
     * 6 - instruction: single character
     */
    SCH(SchPacket.class),
    /**
     * 7 - instruction: state flags pack
     */
    SFP(SfpPacket.class),
    /**
     * 8 - instruction: set car camera
     */
    SCC(SccPacket.class),
    /**
     * 9 - both ways: cam pos pack
     */
    CPP(CppPacket.class),
    /**
     * 10 - info: start multiplayer
     */
    ISM(IsmPacket.class),
    /**
     * 11 - info: message out
     */
    MSO(MsoPacket.class),
    /**
     * 12 - info: hidden /i message
     */
    III(IiiPacket.class),
    /**
     * 13 - instruction: type message or /command
     */
    MST(MstPacket.class),
    /**
     * 14 - instruction: message to a connection
     */
    MTC(MtcPacket.class),
    /**
     * 15 - instruction: set screen mode
     */
    MOD(ModPacket.class),
    /**
     * 16 - info: vote notification
     */
    VTN(VtnPacket.class),
    /**
     * 17 - info: race start
     */
    RST(RstPacket.class),
    /**
     * 18 - info: new connection
     */
    NCN(NcnPacket.class),
    /**
     * 19 - info: connection left
     */
    CNL(CnlPacket.class),
    /**
     * 20 - info: connection renamed
     */
    CPR(CprPacket.class),
    /**
     * 21 - info: new player (joined race)
     */
    NPL(NplPacket.class),
    /**
     * 22 - info: player pit (keeps slot in race)
     */
    PLP(PlpPacket.class),
    /**
     * 23 - info: player leave (spectate - loses slot)
     */
    PLL(PllPacket.class),
    /**
     * 24 - info: lap time
     */
    LAP(LapPacket.class),
    /**
     * 25 - info: split x time
     */
    SPX(SpxPacket.class),
    /**
     * 26 - info: pit stop start
     */
    PIT(PitPacket.class),
    /**
     * 27 - info: pit stop finish
     */
    PSF(PsfPacket.class),
    /**
     * 28 - info: pit lane enter / leave
     */
    PLA(PlaPacket.class),
    /**
     * 29 - info: camera changed
     */
    CCH(CchPacket.class),
    /**
     * 30 - info: penalty given or cleared
     */
    PEN(PenPacket.class),
    /**
     * 31 - info: take over car
     */
    TOC(TocPacket.class),
    /**
     * 32 - info: flag (yellow or blue)
     */
    FLG(FlgPacket.class),
    /**
     * 33 - info: player flags (help flags)
     */
    PFL(PflPacket.class),
    /**
     * 34 - info: finished race
     */
    FIN(FinPacket.class),
    /**
     * 35 - info: result confirmed
     */
    RES(ResPacket.class),
    /**
     * 36 - both ways: reorder (info or instruction)
     */
    REO(ReoPacket.class),
    /**
     * 37 - info: node and lap packet
     */
    NLP(NlpPacket.class),
    /**
     * 38 - info: multi car info
     */
    MCI(MciPacket.class),
    /**
     * 39 - instruction: type message
     */
    MSX(MsxPacket.class),
    /**
     * 40 - instruction: message to local computer
     */
    MSL(MslPacket.class),
    /**
     * 41 - info: car reset
     */
    CRS(CrsPacket.class),
    /**
     * 42 - both ways: delete buttons / receive button requests
     */
    BFN(BfnPacket.class),
    /**
     * 43 - info: autocross layout information
     */
    AXI(AxiPacket.class),
    /**
     * 44 - info: hit an autocross object
     */
    AXO(AxoPacket.class),
    /**
     * 45 - instruction: show a button on local or remote screen
     */
    BTN(BtnPacket.class),
    /**
     * 46 - info: sent when a user clicks a button
     */
    BTC(BtcPacket.class),
    /**
     * 47 - info: sent after typing into a button
     */
    BTT(BttPacket.class),
    /**
     * 48 - both ways: replay information packet
     */
    RIP(RipPacket.class),
    /**
     * 49 - both ways: screenshot
     */
    SSH(SshPacket.class),
    /**
     * 50 - info: contact between cars (collision report)
     */
    CON(ConPacket.class),
    /**
     * 51 - info: contact car + object (collision report)
     */
    OBH(ObhPacket.class),
    /**
     * 52 - info: report incidents that would violate HLVC
     */
    HLV(HlvPacket.class),
    /**
     * 53 - instruction: player cars
     */
    PLC(PlcPacket.class),
    /**
     * 54 - both ways: autocross multiple objects
     */
    AXM(AxmPacket.class),
    /**
     * 55 - info: admin command report
     */
    ACR(AcrPacket.class),
    /**
     * 56 - instruction: car handicaps
     */
    HCP(HcpPacket.class),
    /**
     * 57 - info: new connection - extra info for host
     */
    NCI(NciPacket.class),
    /**
     * 58 - instruction: reply to a join request (allow / disallow)
     */
    JRR(JrrPacket.class),
    /**
     * 59 - info: report InSim checkpoint / InSim circle
     */
    UCO(UcoPacket.class),
    /**
     * 60 - instruction: object control (currently used for lights)
     */
    OCO(OcoPacket.class),
    /**
     * 61 - instruction: multi-purpose - target to connection
     */
    TTC(TtcPacket.class),
    /**
     * 62 - info: connection selected a car
     */
    SLC(SlcPacket.class),
    /**
     * 63 - info: car state changed
     */
    CSC(CscPacket.class),
    /**
     * 64 - info: connection's interface mode
     */
    CIM(CimPacket.class),
    /**
     * 65 - both ways: set mods allowed
     */
    MAL(MalPacket.class),
    /**
     * 66 - both ways: set player handicaps
     */
    PLH(PlhPacket.class),
    /**
     * 67 - both ways: set IP bans
     */
    IPB(IpbPacket.class);

    private final Class<? extends Packet> packetClass;

    PacketType(Class<? extends Packet> packetClass) {
        this.packetClass = packetClass;
    }

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static PacketType fromOrdinal(int ordinal) {
        return EnumHelpers.get(PacketType.class).fromOrdinal(ordinal);
    }

    /**
     * Converts {@link Packet} class to enum value.
     * @param packetClass packet class
     * @return enum value
     */
    public static PacketType fromPacketClass(Class<? extends Packet> packetClass) {
        if (packetClass != null) {
            var enumHelper = EnumHelpers.get(PacketType.class);
            for (var packetType : enumHelper.getAllValuesCached()) {
                if (packetClass.equals(packetType.packetClass)) {
                    return packetType;
                }
            }
        }
        return NONE;
    }
}
