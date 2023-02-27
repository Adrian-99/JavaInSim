package pl.adrian.api.packets.enums;

import pl.adrian.api.packets.*;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.enums.EnumHelpers;

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
    SCH,
    /**
     * 7 - instruction: state flags pack
     */
    SFP(SfpPacket.class),
    /**
     * 8 - instruction: set car camera
     */
    SCC,
    /**
     * 9 - both ways: cam pos pack
     */
    CPP,
    /**
     * 10 - info: start multiplayer
     */
    ISM,
    /**
     * 11 - info: message out
     */
    MSO,
    /**
     * 12 - info: hidden /i message
     */
    III,
    /**
     * 13 - instruction: type message or /command
     */
    MST(MstPacket.class),
    /**
     * 14 - instruction: message to a connection
     */
    MTC,
    /**
     * 15 - instruction: set screen mode
     */
    MOD,
    /**
     * 16 - info: vote notification
     */
    VTN,
    /**
     * 17 - info: race start
     */
    RST,
    /**
     * 18 - info: new connection
     */
    NCN,
    /**
     * 19 - info: connection left
     */
    CNL,
    /**
     * 20 - info: connection renamed
     */
    CPR,
    /**
     * 21 - info: new player (joined race)
     */
    NPL,
    /**
     * 22 - info: player pit (keeps slot in race)
     */
    PLP,
    /**
     * 23 - info: player leave (spectate - loses slot)
     */
    PLL,
    /**
     * 24 - info: lap time
     */
    LAP,
    /**
     * 25 - info: split x time
     */
    SPX,
    /**
     * 26 - info: pit stop start
     */
    PIT,
    /**
     * 27 - info: pit stop finish
     */
    PSF,
    /**
     * 28 - info: pit lane enter / leave
     */
    PLA,
    /**
     * 29 - info: camera changed
     */
    CCH,
    /**
     * 30 - info: penalty given or cleared
     */
    PEN,
    /**
     * 31 - info: take over car
     */
    TOC,
    /**
     * 32 - info: flag (yellow or blue)
     */
    FLG,
    /**
     * 33 - info: player flags (help flags)
     */
    PFL,
    /**
     * 34 - info: finished race
     */
    FIN,
    /**
     * 35 - info: result confirmed
     */
    RES,
    /**
     * 36 - both ways: reorder (info or instruction)
     */
    REO,
    /**
     * 37 - info: node and lap packet
     */
    NLP,
    /**
     * 38 - info: multi car info
     */
    MCI,
    /**
     * 39 - instruction: type message
     */
    MSX,
    /**
     * 40 - instruction: message to local computer
     */
    MSL,
    /**
     * 41 - info: car reset
     */
    CRS,
    /**
     * 42 - both ways: delete buttons / receive button requests
     */
    BFN,
    /**
     * 43 - info: autocross layout information
     */
    AXI,
    /**
     * 44 - info: hit an autocross object
     */
    AXO,
    /**
     * 45 - instruction: show a button on local or remote screen
     */
    BTN,
    /**
     * 46 - info: sent when a user clicks a button
     */
    BTC,
    /**
     * 47 - info: sent after typing into a button
     */
    BTT,
    /**
     * 48 - both ways: replay information packet
     */
    RIP,
    /**
     * 49 - both ways: screenshot
     */
    SSH,
    /**
     * 50 - info: contact between cars (collision report)
     */
    CON,
    /**
     * 51 - info: contact car + object (collision report)
     */
    OBH,
    /**
     * 52 - info: report incidents that would violate HLVC
     */
    HLV,
    /**
     * 53 - instruction: player cars
     */
    PLC,
    /**
     * 54 - both ways: autocross multiple objects
     */
    AXM,
    /**
     * 55 - info: admin command report
     */
    ACR,
    /**
     * 56 - instruction: car handicaps
     */
    HCP,
    /**
     * 57 - info: new connection - extra info for host
     */
    NCI,
    /**
     * 58 - instruction: reply to a join request (allow / disallow)
     */
    JRR,
    /**
     * 59 - info: report InSim checkpoint / InSim circle
     */
    UCO,
    /**
     * 60 - instruction: object control (currently used for lights)
     */
    OCO,
    /**
     * 61 - instruction: multi-purpose - target to connection
     */
    TTC(TtcPacket.class),
    /**
     * 62 - info: connection selected a car
     */
    SLC,
    /**
     * 63 - info: car state changed
     */
    CSC,
    /**
     * 64 - info: connection's interface mode
     */
    CIM,
    /**
     * 65 - both ways: set mods allowed
     */
    MAL;

    private final Class<? extends Packet> packetClass;

    PacketType(Class<? extends Packet> packetClass) {
        this.packetClass = packetClass;
    }

    PacketType() {
        packetClass = null;
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
