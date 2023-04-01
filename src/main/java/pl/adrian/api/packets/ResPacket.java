package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.flags.ConfirmationFlag;
import pl.adrian.api.packets.flags.Flags;
import pl.adrian.api.packets.flags.PlayerFlag;
import pl.adrian.api.packets.structures.Car;
import pl.adrian.internal.packets.annotations.*;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.RequestablePacket;

/**
 * RESult (qualify or confirmed finish).
 */
public class ResPacket extends Packet implements RequestablePacket {
    @Byte
    private final short plid;
    @Char
    @Array(length = 24)
    private final String uName;
    @Char
    @Array(length = 24)
    private final String pName;
    @Char
    @Array(length = 8)
    private final String plate;
    @Char
    @Array(length = 4)
    private final Car car;
    @Unsigned
    private final long tTime;
    @Unsigned
    private final long bTime;
    @Byte
    private final short numStops;
    @Byte
    private final Flags<ConfirmationFlag> confirm;
    @Word
    private final int lapsDone;
    @Word
    private final Flags<PlayerFlag> flags;
    @Byte
    private final short resultNum;
    @Byte
    private final short numRes;
    @Word
    private final int pSeconds;

    /**
     * Creates result packet.
     * @param reqI 0 unless this is a reply to a {@link pl.adrian.api.packets.enums.TinySubtype#RES Tiny RES} request
     * @param plid player's unique id (0 = player left before result was sent)
     * @param uName username
     * @param pName nickname
     * @param plate number plate
     * @param car car
     * @param tTime (ms) race or autocross: total time / qualify: session time
     * @param bTime (ms) best lap
     * @param numStops number of pit stops
     * @param confirm confirmation flags
     * @param lapsDone laps completed
     * @param flags player flags
     * @param resultNum finish or qualify pos (0 = win / 255 = not added to table)
     * @param numRes total number of results (qualify doesn't always add a new one)
     * @param pSeconds penalty time in seconds (already included in race time)
     */
    public ResPacket(short reqI,
                     short plid,
                     String uName,
                     String pName,
                     String plate,
                     Car car,
                     long tTime,
                     long bTime,
                     short numStops,
                     Flags<ConfirmationFlag> confirm,
                     int lapsDone,
                     Flags<PlayerFlag> flags,
                     short resultNum,
                     short numRes,
                     int pSeconds) {
        super(84, PacketType.RES, reqI);
        this.plid = plid;
        this.uName = uName;
        this.pName = pName;
        this.plate = plate;
        this.car = car;
        this.tTime = tTime;
        this.bTime = bTime;
        this.numStops = numStops;
        this.confirm = confirm;
        this.lapsDone = lapsDone;
        this.flags = flags;
        this.resultNum = resultNum;
        this.numRes = numRes;
        this.pSeconds = pSeconds;
    }

    /**
     * @return player's unique id (0 = player left before result was sent)
     */
    public short getPlid() {
        return plid;
    }

    /**
     * @return username
     */
    public String getUName() {
        return uName;
    }

    /**
     * @return nickname
     */
    public String getPName() {
        return pName;
    }

    /**
     * @return number plate
     */
    public String getPlate() {
        return plate;
    }

    /**
     * @return car
     */
    public Car getCar() {
        return car;
    }

    /**
     * @return (ms) race or autocross: total time / qualify: session time
     */
    public long getTTime() {
        return tTime;
    }

    /**
     * @return (ms) best lap
     */
    public long getBTime() {
        return bTime;
    }

    /**
     * @return number of pit stops
     */
    public short getNumStops() {
        return numStops;
    }

    /**
     * @return confirmation flags
     */
    public Flags<ConfirmationFlag> getConfirm() {
        return confirm;
    }

    /**
     * @return laps completed
     */
    public int getLapsDone() {
        return lapsDone;
    }

    /**
     * @return player flags
     */
    public Flags<PlayerFlag> getFlags() {
        return flags;
    }

    /**
     * @return finish or qualify pos (0 = win / 255 = not added to table)
     */
    public short getResultNum() {
        return resultNum;
    }

    /**
     * @return total number of results (qualify doesn't always add a new one)
     */
    public short getNumRes() {
        return numRes;
    }

    /**
     * @return penalty time in seconds (already included in race time)
     */
    public int getpSeconds() {
        return pSeconds;
    }
}
