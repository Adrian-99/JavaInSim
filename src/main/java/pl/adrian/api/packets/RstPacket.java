package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.enums.Wind;
import pl.adrian.api.packets.flags.Flags;
import pl.adrian.api.packets.flags.RaceFlag;
import pl.adrian.internal.packets.annotations.Array;
import pl.adrian.internal.packets.annotations.Char;
import pl.adrian.internal.packets.structures.LapTiming;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.annotations.Word;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.RequestablePacket;
import pl.adrian.internal.packets.structures.RaceLaps;

/**
 * Race STart. The packet is sent by LFS when race starts.
 */
public class RstPacket extends Packet implements RequestablePacket {
    @Byte
    private final RaceLaps raceLaps;
    @Byte
    private final short qualMins;
    @Byte
    private final short numP;
    @Byte
    private final LapTiming timing;
    @Char
    @Array(length = 6)
    private final String track;
    @Byte
    private final short weather;
    @Byte
    private final Wind wind;
    @Word
    private final Flags<RaceFlag> flags;
    @Word
    private final int numNodes;
    @Word
    private final int finish;
    @Word
    private final int split1;
    @Word
    private final int split2;
    @Word
    private final int split3;

    /**
     * Creates race start packet.
     * @param reqI 0 unless this is a reply to an {@link pl.adrian.api.packets.enums.TinySubtype#RST Tiny RST} request
     * @param raceLaps race laps, 0 if qualifying
     * @param qualMins qualifications minutes, 0 if race
     * @param numP number of players in race
     * @param timing lap timing
     * @param track short track name
     * @param weather weather
     * @param wind wind type
     * @param flags race flags
     * @param numNodes total number of nodes in the path
     * @param finish node index - finish line
     * @param split1 node index - split 1
     * @param split2 node index - split 2
     * @param split3 node index - split 3
     */
    @SuppressWarnings("java:S107")
    public RstPacket(short reqI,
                     RaceLaps raceLaps,
                     short qualMins,
                     short numP,
                     LapTiming timing,
                     String track,
                     short weather,
                     Wind wind,
                     Flags<RaceFlag> flags,
                     int numNodes,
                     int finish,
                     int split1,
                     int split2,
                     int split3) {
        super(28, PacketType.RST, reqI);
        this.raceLaps = raceLaps;
        this.qualMins = qualMins;
        this.numP = numP;
        this.timing = timing;
        this.track = track;
        this.weather = weather;
        this.wind = wind;
        this.flags = flags;
        this.numNodes = numNodes;
        this.finish = finish;
        this.split1 = split1;
        this.split2 = split2;
        this.split3 = split3;
    }

    /**
     * @return race laps, 0 if qualifying
     */
    public RaceLaps getRaceLaps() {
        return raceLaps;
    }

    /**
     * @return qualifications minutes, 0 if race
     */
    public short getQualMins() {
        return qualMins;
    }

    /**
     * @return number of players in race
     */
    public short getNumP() {
        return numP;
    }

    /**
     * @return lap timing
     */
    public LapTiming getTiming() {
        return timing;
    }

    /**
     * @return short track name
     */
    public String getTrack() {
        return track;
    }

    /**
     * @return weather
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

    /**
     * @return race flags
     */
    public Flags<RaceFlag> getFlags() {
        return flags;
    }

    /**
     * @return total number of nodes in the path
     */
    public int getNumNodes() {
        return numNodes;
    }

    /**
     * @return node index - finish line
     */
    public int getFinish() {
        return finish;
    }

    /**
     * @return node index - split 1
     */
    public int getSplit1() {
        return split1;
    }

    /**
     * @return node index - split 2
     */
    public int getSplit2() {
        return split2;
    }

    /**
     * @return node index - split 3
     */
    public int getSplit3() {
        return split3;
    }
}
