package pl.adrian.api.packets.enums;

/**
 * Enumeration for subtype of tiny packet
 */
public enum TinySubtype {
    /**
     * 0 - keep alive: see "maintaining the connection" in InSim docs
     */
    NONE,
    /**
     * 1 - info request: get version
     */
    VER,
    /**
     * 2 - instruction: close insim
     */
    CLOSE,
    /**
     * 3 - ping request: external progam requesting a reply
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
    SST,
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
    ISM,
    /**
     * 11 - info: race end (return to race setup screen)
     */
    REN,
    /**
     * 12 - info: all players cleared from race
     */
    CLR,
    /**
     * 13 - info request: get NCN for all connections
     */
    NCN,
    /**
     * 14 - info request: get all players
     */
    NPL,
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
     * 19 - info request: send an IS_RST
     */
    RST,
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
     * 23 - info request: get NCI for all guests (on host only)
     */
    NCI,
    /**
     * 24 - info request: send a SMALL_ALC (allowed cars)
     */
    ALC,
    /**
     * 25 - info request: send IS_AXM packets for the entire layout
     */
    AXM,
    /**
     * 26 - info request: send IS_SLC packets for all connections
     */
    SLC,
    /**
     * 27 - info request: send IS_MAL listing the currently allowed mods
     */
    MAL;

    private static TinySubtype[] allValuesCached = null;

    /**
     * Converts ordinal number to enum value
     * @param ordinal ordinal number
     * @return enum value
     */
    public static TinySubtype fromOrdinal(int ordinal) {
        if (allValuesCached == null) {
            allValuesCached = values();
        }
        return allValuesCached[ordinal];
    }
}
