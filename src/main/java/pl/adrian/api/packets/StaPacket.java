package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.*;
import pl.adrian.api.packets.flags.Flags;
import pl.adrian.api.packets.flags.StaFlag;
import pl.adrian.internal.packets.annotations.*;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.annotations.Float;
import pl.adrian.api.packets.structures.RaceLaps;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.RequestablePacket;
import pl.adrian.internal.packets.util.PacketDataBytes;

/**
 * STAte. LFS will send this packet any time the info in it changes.
 */
public class StaPacket extends Packet implements RequestablePacket {
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
     * @param reqI non-zero if replying to a request packet
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
}
