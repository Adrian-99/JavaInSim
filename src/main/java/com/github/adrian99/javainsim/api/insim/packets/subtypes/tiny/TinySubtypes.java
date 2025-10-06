/*
 * Copyright (c) 2025, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.github.adrian99.javainsim.api.insim.packets.subtypes.tiny;

import com.github.adrian99.javainsim.api.insim.packets.*;
import com.github.adrian99.javainsim.api.insim.packets.subtypes.small.SmallSubtypes;

import java.util.ArrayList;
import java.util.List;

/**
 * Enumeration for subtype of {@link TinyPacket}.
 */
@SuppressWarnings("unused")
public class TinySubtypes {
    static final List<TinySubtype> ALL = new ArrayList<>();

    private TinySubtypes() {}

    /**
     * 0 - keep alive: see "maintaining the connection" in InSim docs
     */
    public static final TinySubtype NONE = new TinySubtype(0);
    /**
     * 1 - info request: get {@link VerPacket}
     */
    public static final TinyRequestingSubtype<VerPacket> VER = new TinyRequestingSubtype<>(1, VerPacket.class, true);
    /**
     * 2 - instruction: close insim
     */
    public static final TinySubtype CLOSE = new TinySubtype(2);
    /**
     * 3 - ping request: external program requesting a reply
     */
    public static final TinySubtype PING = new TinySubtype(3);
    /**
     * 4 - ping reply: reply to a ping request
     */
    public static final TinySubtype REPLY = new TinySubtype(4);
    /**
     * 5 - both ways: game vote cancel (info or request)
     */
    public static final TinySubtype VTC = new TinySubtype(5);
    /**
     * 6 - info request: send camera pos (i.e. {@link CppPacket})
     */
    public static final TinyRequestingSubtype<CppPacket> SCP = new TinyRequestingSubtype<>(6, CppPacket.class, true);
    /**
     * 7 - info request: send state info (i.e. {@link StaPacket})
     */
    public static final TinyRequestingSubtype<StaPacket> SST = new TinyRequestingSubtype<>(7, StaPacket.class, true);
    /**
     * 8 - info request: get time in hundredths (i.e. {@link SmallSubtypes#RTP RTP} {@link SmallPacket})
     */
    public static final TinyRequestingSubtype<SmallPacket> GTH = new TinyRequestingSubtype<>(8, SmallPacket.class, true);
    /**
     * 9 - info: multi player end
     */
    public static final TinySubtype MPE = new TinySubtype(9);
    /**
     * 10 - info request: get multiplayer info (i.e. {@link IsmPacket})
     */
    public static final TinyRequestingSubtype<IsmPacket> ISM = new TinyRequestingSubtype<>(10, IsmPacket.class, true);
    /**
     * 11 - info: race end (return to race setup screen)
     */
    public static final TinySubtype REN = new TinySubtype(11);
    /**
     * 12 - info: all players cleared from race
     */
    public static final TinySubtype CLR = new TinySubtype(12);
    /**
     * 13 - info request: get {@link NcnPacket} for all connections
     */
    public static final TinyRequestingSubtype<NcnPacket> NCN = new TinyRequestingSubtype<>(13, NcnPacket.class, false);
    /**
     * 14 - info request: get {@link NplPacket} for all players
     */
    public static final TinyRequestingSubtype<NplPacket> NPL = new TinyRequestingSubtype<>(14, NplPacket.class, false);
    /**
     * 15 - info request: get {@link ResPacket} for all players
     */
    public static final TinyRequestingSubtype<ResPacket> RES = new TinyRequestingSubtype<>(15, ResPacket.class, false);
    /**
     * 16 - info request: send an {@link NlpPacket}
     */
    public static final TinyRequestingSubtype<NlpPacket> NLP = new TinyRequestingSubtype<>(16, NlpPacket.class, true);
    /**
     * 17 - info request: send an {@link MciPacket}
     */
    public static final TinyRequestingSubtype<MciPacket> MCI = new TinyRequestingSubtype<>(17, MciPacket.class, false);
    /**
     * 18 - info request: send an {@link ReoPacket}
     */
    public static final TinyRequestingSubtype<ReoPacket> REO = new TinyRequestingSubtype<>(18, ReoPacket.class, true);
    /**
     * 19 - info request: send an {@link RstPacket}
     */
    public static final TinyRequestingSubtype<RstPacket> RST = new TinyRequestingSubtype<>(19, RstPacket.class, true);
    /**
     * 20 - info request: send an {@link AxiPacket} - AutoX Info
     */
    public static final TinyRequestingSubtype<AxiPacket> AXI = new TinyRequestingSubtype<>(20, AxiPacket.class, true);
    /**
     * 21 - info: autocross cleared
     */
    public static final TinySubtype AXC = new TinySubtype(21);
    /**
     * 22 - info request: send an {@link RipPacket} - Replay Information Packet
     */
    public static final TinyRequestingSubtype<RipPacket> RIP = new TinyRequestingSubtype<>(22, RipPacket.class, true);
    /**
     * 23 - info request: get {@link NciPacket} for all guests (on host only)
     */
    public static final TinyRequestingSubtype<NciPacket> NCI = new TinyRequestingSubtype<>(23, NciPacket.class, false);
    /**
     * 24 - info request: send a {@link SmallSubtypes#ALC ALC} {@link SmallPacket} (allowed cars)
     */
    public static final TinyRequestingSubtype<SmallPacket> ALC = new TinyRequestingSubtype<>(24, SmallPacket.class, true);
    /**
     * 25 - info request: send {@link AxmPacket} packets for the entire layout
     */
    public static final TinyRequestingSubtype<AxmPacket> AXM = new TinyRequestingSubtype<>(25, AxmPacket.class, false);
    /**
     * 26 - info request: send {@link SlcPacket} packets for all connections
     */
    public static final TinyRequestingSubtype<SlcPacket> SLC = new TinyRequestingSubtype<>(26, SlcPacket.class, false);
    /**
     * 27 - info request: send {@link MalPacket} listing the allowed mods
     */
    public static final TinyRequestingSubtype<MalPacket> MAL = new TinyRequestingSubtype<>(27, MalPacket.class, true);
    /**
     * 28 - info request: send {@link PlhPacket} listing player handicaps
     */
    public static final TinyRequestingSubtype<PlhPacket> PLH = new TinyRequestingSubtype<>(28, PlhPacket.class, true);
    /**
     * 29 - info request: send {@link IpbPacket} listing the IP bans
     */
    public static final TinyRequestingSubtype<IpbPacket> IPB = new TinyRequestingSubtype<>(29, IpbPacket.class, true);
}
