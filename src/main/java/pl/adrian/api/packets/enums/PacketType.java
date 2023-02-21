package pl.adrian.api.packets.enums;

public enum PacketType {
    NONE,		//  0					: not used
    ISI,		//  1 - instruction		: insim initialise
    VER,		//  2 - info			: version info
    TINY,		//  3 - both ways		: multi purpose
    SMALL,		//  4 - both ways		: multi purpose
    STA,		//  5 - info			: state info
    SCH,		//  6 - instruction		: single character
    SFP,		//  7 - instruction		: state flags pack
    SCC,		//  8 - instruction		: set car camera
    CPP,		//  9 - both ways		: cam pos pack
    ISM,		// 10 - info			: start multiplayer
    MSO,		// 11 - info			: message out
    III,		// 12 - info			: hidden /i message
    MST,		// 13 - instruction		: type message or /command
    MTC,		// 14 - instruction		: message to a connection
    MOD,		// 15 - instruction		: set screen mode
    VTN,		// 16 - info			: vote notification
    RST,		// 17 - info			: race start
    NCN,		// 18 - info			: new connection
    CNL,		// 19 - info			: connection left
    CPR,		// 20 - info			: connection renamed
    NPL,		// 21 - info			: new player (joined race)
    PLP,		// 22 - info			: player pit (keeps slot in race)
    PLL,		// 23 - info			: player leave (spectate - loses slot)
    LAP,		// 24 - info			: lap time
    SPX,		// 25 - info			: split x time
    PIT,		// 26 - info			: pit stop start
    PSF,		// 27 - info			: pit stop finish
    PLA,		// 28 - info			: pit lane enter / leave
    CCH,		// 29 - info			: camera changed
    PEN,		// 30 - info			: penalty given or cleared
    TOC,		// 31 - info			: take over car
    FLG,		// 32 - info			: flag (yellow or blue)
    PFL,		// 33 - info			: player flags (help flags)
    FIN,		// 34 - info			: finished race
    RES,		// 35 - info			: result confirmed
    REO,		// 36 - both ways		: reorder (info or instruction)
    NLP,		// 37 - info			: node and lap packet
    MCI,		// 38 - info			: multi car info
    MSX,		// 39 - instruction		: type message
    MSL,		// 40 - instruction		: message to local computer
    CRS,		// 41 - info			: car reset
    BFN,		// 42 - both ways		: delete buttons / receive button requests
    AXI,		// 43 - info			: autocross layout information
    AXO,		// 44 - info			: hit an autocross object
    BTN,		// 45 - instruction		: show a button on local or remote screen
    BTC,		// 46 - info			: sent when a user clicks a button
    BTT,		// 47 - info			: sent after typing into a button
    RIP,		// 48 - both ways		: replay information packet
    SSH,		// 49 - both ways		: screenshot
    CON,		// 50 - info			: contact between cars (collision report)
    OBH,		// 51 - info			: contact car + object (collision report)
    HLV,		// 52 - info			: report incidents that would violate HLVC
    PLC,		// 53 - instruction		: player cars
    AXM,		// 54 - both ways		: autocross multiple objects
    ACR,		// 55 - info			: admin command report
    HCP,		// 56 - instruction		: car handicaps
    NCI,		// 57 - info			: new connection - extra info for host
    JRR,		// 58 - instruction		: reply to a join request (allow / disallow)
    UCO,		// 59 - info			: report InSim checkpoint / InSim circle
    OCO,		// 60 - instruction		: object control (currently used for lights)
    TTC,		// 61 - instruction		: multi purpose - target to connection
    SLC,		// 62 - info			: connection selected a car
    CSC,		// 63 - info			: car state changed
    CIM,		// 64 - info			: connection's interface mode
    MAL;		// 65 - both ways		: set mods allowed

    private static PacketType[] allValuesCached = null;

    public static PacketType fromOrdinal(int ordinal) {
        if (allValuesCached == null) {
            allValuesCached = values();
        }
        return allValuesCached[ordinal];
    }
}
