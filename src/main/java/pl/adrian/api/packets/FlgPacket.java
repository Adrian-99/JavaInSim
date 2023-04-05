package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.FlagType;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.base.InfoPacket;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.util.PacketDataBytes;

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
     * Creates flag packet. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public FlgPacket(PacketDataBytes packetDataBytes) {
        super(8, PacketType.FLG, 0);
        plid = packetDataBytes.readByte();
        isOn = packetDataBytes.readByte() != 0;
        flag = FlagType.fromOrdinal(packetDataBytes.readByte());
        carBehind = packetDataBytes.readByte();
        packetDataBytes.skipZeroByte();
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
