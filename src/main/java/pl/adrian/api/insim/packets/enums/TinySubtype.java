/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.insim.packets.enums;

import pl.adrian.api.insim.packets.*;
import pl.adrian.internal.insim.packets.base.RequestablePacket;
import pl.adrian.internal.insim.packets.structures.base.ByteInstructionStructure;

import java.util.ArrayList;
import java.util.List;

/**
 * Enumeration for subtype of {@link TinyPacket}.
 */
@SuppressWarnings({"referencing", "StaticInitializerReferencesSubClass", "unused"})
public class TinySubtype implements ByteInstructionStructure {
    private final short value;

    private TinySubtype(int value) {
        this.value = (short) value;
        ALL.add(this);
    }

    /**
     * @return ordinal value of the subtype
     */
    public short getByteValue() {
        return value;
    }

    /**
     * Enumeration for subtype of {@link TinyPacket} that serve as a request for other packet.
     * @param <T> class of the packet that is requested by the subtype
     */
    public static final class Requesting<T extends RequestablePacket> extends TinySubtype {
        private final Class<T> requestingPacketClass;
        private final boolean multiPacketResponse;

        private Requesting(int value, Class<T> requestingPacketClass, boolean multiPacketResponse) {
            super(value);
            this.requestingPacketClass = requestingPacketClass;
            this.multiPacketResponse = multiPacketResponse;
            ALL.add(this);
        }

        private static final List<Requesting<?>> ALL = new ArrayList<>();

        /**
         * @return class of the packet that is a response to packet of this subtype
         */
        public Class<T> getRequestingPacketClass() {
            return requestingPacketClass;
        }

        /**
         * @return whether multiple packets are expected in response to request of this type
         */
        public boolean isMultiPacketResponse() {
            return multiPacketResponse;
        }

        /**
         * Converts {@link RequestablePacket} class to enum value.
         * @param packetClass class of the packet
         * @return enum value
         * @param <T> type of the packet
         */
        @SuppressWarnings("unchecked")
        public static <T extends RequestablePacket> Requesting<T> fromRequestablePacketClass(Class<T> packetClass) {
            for (var tinySubtype : ALL) {
                if (tinySubtype.requestingPacketClass.equals(packetClass)) {
                    return (Requesting<T>) tinySubtype;
                }
            }
            return null;
        }
    }

    private static final List<TinySubtype> ALL = new ArrayList<>();

    /**
     * 0 - keep alive: see "maintaining the connection" in InSim docs
     */
    public static final TinySubtype NONE = new TinySubtype(0);
    /**
     * 1 - info request: get version
     */
    public static final Requesting<VerPacket> VER = new Requesting<>(1, VerPacket.class, false);
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
     * 6 - info request: send camera pos
     */
    public static final Requesting<CppPacket> SCP = new Requesting<>(6, CppPacket.class, false);
    /**
     * 7 - info request: send state info
     */
    public static final Requesting<StaPacket> SST = new Requesting<>(7, StaPacket.class, false);
    /**
     * 8 - info request: get time in hundredths (i.e. {@link SmallSubtype#RTP RTP} {@link SmallPacket})
     */
    public static final Requesting<SmallPacket> GTH = new Requesting<>(8, SmallPacket.class, false);
    /**
     * 9 - info: multi player end
     */
    public static final TinySubtype MPE = new TinySubtype(9);
    /**
     * 10 - info request: get multiplayer info (i.e. {@link IsmPacket})
     */
    public static final Requesting<IsmPacket> ISM = new Requesting<>(10, IsmPacket.class, false);
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
    public static final Requesting<NcnPacket> NCN = new Requesting<>(13, NcnPacket.class, true);
    /**
     * 14 - info request: get {@link NplPacket} for all players
     */
    public static final Requesting<NplPacket> NPL = new Requesting<>(14, NplPacket.class, true);
    /**
     * 15 - info request: get {@link ResPacket} for all players
     */
    public static final Requesting<ResPacket> RES = new Requesting<>(15, ResPacket.class, true);
    /**
     * 16 - info request: send an {@link NlpPacket}
     */
    public static final Requesting<NlpPacket> NLP = new Requesting<>(16, NlpPacket.class, false);
    /**
     * 17 - info request: send an {@link MciPacket}
     */
    public static final Requesting<MciPacket> MCI = new Requesting<>(17, MciPacket.class, true);
    /**
     * 18 - info request: send an {@link ReoPacket}
     */
    public static final Requesting<ReoPacket> REO = new Requesting<>(18, ReoPacket.class, false);
    /**
     * 19 - info request: send an {@link RstPacket}
     */
    public static final Requesting<RstPacket> RST = new Requesting<>(19, RstPacket.class, false);
    /**
     * 20 - info request: send an {@link AxiPacket} - AutoX Info
     */
    public static final Requesting<AxiPacket> AXI = new Requesting<>(20, AxiPacket.class, false);
    /**
     * 21 - info: autocross cleared
     */
    public static final TinySubtype AXC = new TinySubtype(21);
    /**
     * 22 - info request: send an {@link RipPacket} - Replay Information Packet
     */
    public static final Requesting<RipPacket> RIP = new Requesting<>(22, RipPacket.class, false);
    /**
     * 23 - info request: get {@link NciPacket} for all guests (on host only)
     */
    public static final Requesting<NciPacket> NCI = new Requesting<>(23, NciPacket.class, true);
    /**
     * 24 - info request: send a {@link SmallSubtype#ALC ALC} {@link SmallPacket} (allowed cars)
     */
    public static final Requesting<SmallPacket> ALC = new Requesting<>(24, SmallPacket.class, false);
    /**
     * 25 - info request: send {@link AxmPacket} packets for the entire layout
     */
    public static final Requesting<AxmPacket> AXM = new Requesting<>(25, AxmPacket.class, true);
    /**
     * 26 - info request: send {@link SlcPacket} packets for all connections
     */
    public static final Requesting<SlcPacket> SLC = new Requesting<>(26, SlcPacket.class, true);
    /**
     * 27 - info request: send {@link MalPacket} listing the currently allowed mods
     */
    public static final Requesting<MalPacket> MAL = new Requesting<>(27, MalPacket.class, false);

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static TinySubtype fromOrdinal(int ordinal) {
        for (var tinySubtype : ALL) {
            if (tinySubtype.getByteValue() == ordinal) {
                return tinySubtype;
            }
        }
        return NONE;
    }
}
