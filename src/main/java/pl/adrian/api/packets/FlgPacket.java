package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.FlagType;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.base.InfoPacket;
import pl.adrian.internal.packets.base.Packet;

/**
 * FLaG. The packet is sent by LFS when yellow or blue flag changes.
 */
public class FlgPacket extends Packet implements InfoPacket {
    @Byte
    private final short plid;
    @Byte
    private final boolean isOn;
    @Byte
    private final FlagType flag;
    @Byte
    private final short carBehind;

    /**
     * Creates flag packet.
     * @param plid player's unique id
     * @param isOn whether flag is on
     * @param flag flag type
     * @param carBehind unique id of obstructed player
     */
    public FlgPacket(short plid, boolean isOn, FlagType flag, short carBehind) {
        super(8, PacketType.FLG, 0);
        this.plid = plid;
        this.isOn = isOn;
        this.flag = flag;
        this.carBehind = carBehind;
    }

    /**
     * @return player's unique id
     */
    public short getPlid() {
        return plid;
    }

    /**
     * @return whether flag is on
     */
    public boolean isOn() {
        return isOn;
    }

    /**
     * @return flag type
     */
    public FlagType getFlag() {
        return flag;
    }

    /**
     * @return unique id of obstructed player
     */
    public short getCarBehind() {
        return carBehind;
    }
}
