package pl.adrian.api.packets.enums;

import pl.adrian.api.packets.*;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.RequestablePacket;
import pl.adrian.internal.packets.enums.EnumHelpers;

/**
 * Enumeration for subtype of tiny packet.
 */
public enum TinySubtype {
    /**
     * 0 - keep alive: see "maintaining the connection" in InSim docs
     */
    NONE,
    /**
     * 1 - info request: get version
     */
    VER(VerPacket.class),
    /**
     * 2 - instruction: close insim
     */
    CLOSE,
    /**
     * 3 - ping request: external program requesting a reply
     */
    PING,
    /**
     * 4 - ping reply: reply to a ping request
     */
    REPLY,
    /**
     * 5 - both ways: game vote cancel (info or request)
     */
    VTC,
    /**
     * 6 - info request: send camera pos
     */
    SCP,
    /**
     * 7 - info request: send state info
     */
    SST(StaPacket.class),
    /**
     * 8 - info request: get time in hundredths (i.e. SMALL_RTP)
     */
    GTH,
    /**
     * 9 - info: multi player end
     */
    MPE,
    /**
     * 10 - info request: get multiplayer info (i.e. ISP_ISM)
     */
    ISM(IsmPacket.class),
    /**
     * 11 - info: race end (return to race setup screen)
     */
    REN,
    /**
     * 12 - info: all players cleared from race
     */
    CLR,
    /**
     * 13 - info request: get {@link NcnPacket} for all connections
     */
    NCN(NcnPacket.class, true),
    /**
     * 14 - info request: get {@link NplPacket} for all players
     */
    NPL(NplPacket.class, true),
    /**
     * 15 - info request: get all results
     */
    RES,
    /**
     * 16 - info request: send an IS_NLP
     */
    NLP,
    /**
     * 17 - info request: send an IS_MCI
     */
    MCI,
    /**
     * 18 - info request: send an IS_REO
     */
    REO,
    /**
     * 19 - info request: send an {@link RstPacket}
     */
    RST(RstPacket.class),
    /**
     * 20 - info request: send an IS_AXI - AutoX Info
     */
    AXI,
    /**
     * 21 - info: autocross cleared
     */
    AXC,
    /**
     * 22 - info request: send an IS_RIP - Replay Information Packet
     */
    RIP,
    /**
     * 23 - info request: get {@link NciPacket} for all guests (on host only)
     */
    NCI(NciPacket.class, true),
    /**
     * 24 - info request: send a SMALL_ALC (allowed cars)
     */
    ALC,
    /**
     * 25 - info request: send IS_AXM packets for the entire layout
     */
    AXM,
    /**
     * 26 - info request: send {@link SlcPacket} packets for all connections
     */
    SLC(SlcPacket.class, true),
    /**
     * 27 - info request: send {@link MalPacket} listing the currently allowed mods
     */
    MAL(MalPacket.class);

    private final Class<? extends RequestablePacket> requestablePacketClass;
    private final boolean multiPacketResponse;

    TinySubtype(Class<? extends RequestablePacket> requestablePacketClass, boolean multiPacketResponse) {
        this.requestablePacketClass = requestablePacketClass;
        this.multiPacketResponse = multiPacketResponse;
    }

    TinySubtype(Class<? extends RequestablePacket> requestablePacketClass) {
        this.requestablePacketClass = requestablePacketClass;
        this.multiPacketResponse = false;
    }

    TinySubtype() {
        this.requestablePacketClass = null;
        this.multiPacketResponse = false;
    }

    /**
     * @return whether multiple packets are expected in response to request of this type
     */
    public boolean isMultiPacketResponse() {
        return multiPacketResponse;
    }

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static TinySubtype fromOrdinal(int ordinal) {
        return EnumHelpers.get(TinySubtype.class).fromOrdinal(ordinal);
    }

    /**
     * Converts {@link RequestablePacket} class to enum value.
     * @param packetClass class of the packet
     * @return enum value
     * @param <T> type of the packet
     */
    public static <T extends Packet & RequestablePacket> TinySubtype fromRequestablePacketClass(Class<T> packetClass) {
        if (packetClass != null) {
            var enumHelper = EnumHelpers.get(TinySubtype.class);
            for (var tinySubtype : enumHelper.getAllValuesCached()) {
                if (packetClass.equals(tinySubtype.requestablePacketClass)) {
                    return tinySubtype;
                }
            }
        }
        return NONE;
    }
}
