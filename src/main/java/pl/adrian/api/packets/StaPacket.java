package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.*;
import pl.adrian.api.packets.flags.Flags;
import pl.adrian.api.packets.flags.StaFlag;
import pl.adrian.internal.packets.util.RaceLaps;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.annotations.CharArray;
import pl.adrian.internal.packets.annotations.Float;
import pl.adrian.internal.packets.annotations.Word;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.RequestablePacket;

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
    @CharArray(length = 6)
    private final String track;
    @Byte
    private final short weather;
    @Byte
    private final Wind wind;

    /**
     * Creates state packet.
     * @param reqI non-zero if replying to a request packet
     * @param replaySpeed replay speed - 1.0 is normal speed
     * @param flags state flags
     * @param inGameCam which type of camera is selected
     * @param viewPlid unique ID of viewed player (0 = none)
     * @param numP number of players in race
     * @param numConns number of connections including host
     * @param numFinished number finished or qualified
     * @param raceInProg race progress
     * @param qualMins number of minutes of qualifications
     * @param raceLaps number of race laps (or hours)
     * @param serverStatus server status
     * @param track short name for track e.g. FE2R
     * @param weather weather - 0,1,2...
     * @param wind wind type
     */
    @SuppressWarnings("java:S107")
    public StaPacket(int reqI,
                     float replaySpeed,
                     Flags<StaFlag> flags,
                     ViewIdentifier inGameCam,
                     short viewPlid,
                     short numP,
                     short numConns,
                     short numFinished,
                     RaceProgress raceInProg,
                     short qualMins,
                     RaceLaps raceLaps,
                     ServerStatus serverStatus,
                     String track,
                     short weather,
                     Wind wind) {
        super(28, PacketType.STA, reqI);
        this.replaySpeed = replaySpeed;
        this.flags = flags;
        this.inGameCam = inGameCam;
        this.viewPlid = viewPlid;
        this.numP = numP;
        this.numConns = numConns;
        this.numFinished = numFinished;
        this.raceInProg = raceInProg;
        this.qualMins = qualMins;
        this.raceLaps = raceLaps;
        this.serverStatus = serverStatus;
        this.track = track;
        this.weather = weather;
        this.wind = wind;
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
