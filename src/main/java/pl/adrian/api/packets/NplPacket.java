package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.enums.TyreCompound;
import pl.adrian.api.packets.flags.*;
import pl.adrian.api.packets.structures.Car;
import pl.adrian.internal.packets.annotations.Array;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.annotations.Char;
import pl.adrian.internal.packets.annotations.Word;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.RequestablePacket;

import java.util.List;

/**
 * New PLayer joining race. The packet is sent by LFS when player joins race
 * (if plid already exists, then leaving pits).
 */
public class NplPacket extends Packet implements RequestablePacket {
    @Byte
    private final short plid;
    @Byte
    private final short ucid;
    @Byte
    private final Flags<PlayerTypeFlag> pType;
    @Word
    private final Flags<PlayerFlag> flags;
    @Char
    @Array(length = 24)
    private final String pName;
    @Char
    @Array(length = 8)
    private final String plate;
    @Char
    @Array(length = 4)
    private final Car car;
    @Char
    @Array(length = 16)
    private final String sName;
    @Byte
    @Array(length = 4)
    private final List<TyreCompound> tyres;
    @Byte
    private final short hMass;
    @Byte
    private final short hTRes;
    @Byte
    private final short model;
    @Byte
    private final Flags<PassengerFlag> pass;
    @Byte
    private final short rWAdj;
    @Byte
    private final short fWAdj;
    @Byte
    private final Flags<SetupFlag> setF;
    @Byte
    private final short numP;
    @Byte
    private final short config;
    @Byte
    private final short fuel;

    /**
     * Creates new player joining race packet.
     * @param reqI 0 unless this is a reply to an {@link pl.adrian.api.packets.enums.TinySubtype#NPL Tiny NPL} request
     * @param plid player's newly assigned unique id
     * @param ucid connection's unique id
     * @param pType player type flags
     * @param flags player flags
     * @param pName nickname
     * @param plate number plate
     * @param car car
     * @param sName skin name
     * @param tyres compounds
     * @param hMass added mass (kg)
     * @param hTRes intake restriction
     * @param model driver model
     * @param pass passenger flags
     * @param rWAdj low 4 bits: tyre width reduction (rear)
     * @param fWAdj low 4 bits: tyre width reduction (front)
     * @param setF setup flags
     * @param numP number in race - ZERO if this is a join request
     * @param config car configuration
     * @param fuel fuel percent (if /showfuel yes, 255 otherwise)
     */
    @SuppressWarnings("java:S107")
    public NplPacket(short reqI,
                     short plid,
                     short ucid,
                     Flags<PlayerTypeFlag> pType,
                     Flags<PlayerFlag> flags,
                     String pName,
                     String plate,
                     Car car,
                     String sName,
                     List<TyreCompound> tyres,
                     short hMass,
                     short hTRes,
                     short model,
                     Flags<PassengerFlag> pass,
                     short rWAdj,
                     short fWAdj,
                     Flags<SetupFlag> setF,
                     short numP,
                     short config,
                     short fuel) {
        super(76, PacketType.NPL, reqI);
        this.plid = plid;
        this.ucid = ucid;
        this.pType = pType;
        this.flags = flags;
        this.pName = pName;
        this.plate = plate;
        this.car = car;
        this.sName = sName;
        this.tyres = tyres;
        this.hMass = hMass;
        this.hTRes = hTRes;
        this.model = model;
        this.pass = pass;
        this.rWAdj = rWAdj;
        this.fWAdj = fWAdj;
        this.setF = setF;
        this.numP = numP;
        this.config = config;
        this.fuel = fuel;
    }

    /**
     * @return player's newly assigned unique id
     */
    public short getPlid() {
        return plid;
    }

    /**
     * @return connection's unique id
     */
    public short getUcid() {
        return ucid;
    }

    /**
     * NOTE: PType bit 0 (female) is not reported on dedicated host as humans are not loaded.
     * You can use the driver model byte instead if required (and to force the use of helmets).
     * @return player type flags
     */
    public Flags<PlayerTypeFlag> getPType() {
        return pType;
    }

    /**
     * @return player flags
     */
    public Flags<PlayerFlag> getFlags() {
        return flags;
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
     * @return skin name
     */
    public String getSName() {
        return sName;
    }

    /**
     * @return compounds
     */
    public List<TyreCompound> getTyres() {
        return tyres;
    }

    /**
     * @return added mass (kg)
     */
    public short getHMass() {
        return hMass;
    }

    /**
     * @return intake restriction
     */
    public short getHTRes() {
        return hTRes;
    }

    /**
     * @return driver model
     */
    public short getModel() {
        return model;
    }

    /**
     * @return passenger flags
     */
    public Flags<PassengerFlag> getPass() {
        return pass;
    }

    /**
     * @return low 4 bits: tyre width reduction (rear)
     */
    public short getRWAdj() {
        return rWAdj;
    }

    /**
     * @return low 4 bits: tyre width reduction (front)
     */
    public short getFWAdj() {
        return fWAdj;
    }

    /**
     * @return setup flags
     */
    public Flags<SetupFlag> getSetF() {
        return setF;
    }

    /**
     * @return number in race - ZERO if this is a join request
     */
    public short getNumP() {
        return numP;
    }

    /**
     * @return car configuration
     */
    public short getConfig() {
        return config;
    }

    /**
     * @return fuel percent (if /showfuel yes), 255 otherwise
     */
    public short getFuel() {
        return fuel;
    }
}
