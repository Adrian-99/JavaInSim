package pl.adrian.api.packets.enums;

public enum TinySubtype {
    NONE,		//  0 - keep alive		: see "maintaining the connection"
    VER,		//  1 - info request	: get version
    CLOSE,		//  2 - instruction		: close insim
    PING,		//  3 - ping request	: external progam requesting a reply
    REPLY,		//  4 - ping reply		: reply to a ping request
    VTC,		//  5 - both ways		: game vote cancel (info or request)
    SCP,		//  6 - info request	: send camera pos
    SST,		//  7 - info request	: send state info
    GTH,		//  8 - info request	: get time in hundredths (i.e. SMALL_RTP)
    MPE,		//  9 - info			: multi player end
    ISM,		// 10 - info request	: get multiplayer info (i.e. ISP_ISM)
    REN,		// 11 - info			: race end (return to race setup screen)
    CLR,		// 12 - info			: all players cleared from race
    NCN,		// 13 - info request	: get NCN for all connections
    NPL,		// 14 - info request	: get all players
    RES,		// 15 - info request	: get all results
    NLP,		// 16 - info request	: send an IS_NLP
    MCI,		// 17 - info request	: send an IS_MCI
    REO,		// 18 - info request	: send an IS_REO
    RST,		// 19 - info request	: send an IS_RST
    AXI,		// 20 - info request	: send an IS_AXI - AutoX Info
    AXC,		// 21 - info			: autocross cleared
    RIP,		// 22 - info request	: send an IS_RIP - Replay Information Packet
    NCI,		// 23 - info request	: get NCI for all guests (on host only)
    ALC,		// 24 - info request	: send a SMALL_ALC (allowed cars)
    AXM,		// 25 - info request	: send IS_AXM packets for the entire layout
    SLC,		// 26 - info request	: send IS_SLC packets for all connections
    MAL;		// 27 - info request	: send IS_MAL listing the currently allowed mods

    private static TinySubtype[] allValuesCached = null;

    public static TinySubtype fromOrdinal(int ordinal) {
        if (allValuesCached == null) {
            allValuesCached = values();
        }
        return allValuesCached[ordinal];
    }
}
