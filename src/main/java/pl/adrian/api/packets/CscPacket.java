package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.CscAction;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.structures.CarContOBJ;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.annotations.Structure;
import pl.adrian.internal.packets.annotations.Unsigned;
import pl.adrian.internal.packets.base.InfoPacket;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.util.PacketDataBytes;

/**
 * Car State Changed. The packet is sent when car of any player changes state (currently start or stop).
 */
public class CscPacket extends Packet implements InfoPacket {
    @Byte
    private final short plid;
    @Byte
    private final CscAction cscAction;
    @Unsigned
    private final long time;
    @Structure
    private final CarContOBJ c;

    /**
     * Creates car state change packet. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public CscPacket(PacketDataBytes packetDataBytes) {
        super(20, PacketType.CSC, 0);
        plid = packetDataBytes.readByte();
        packetDataBytes.skipZeroByte();
        cscAction = CscAction.fromOrdinal(packetDataBytes.readByte());
        packetDataBytes.skipZeroBytes(2);
        time = packetDataBytes.readUnsigned();
        c = new CarContOBJ(packetDataBytes);
    }

    /**
     * @return player's unique id
     */
    public short getPlid() {
        return plid;
    }

    /**
     * @return reported action
     */
    public CscAction getCscAction() {
        return cscAction;
    }

    /**
     * @return hundredths of a second since start (as in {@link pl.adrian.api.packets.enums.SmallSubtype#RTP Small RTP})
     */
    public long getTime() {
        return time;
    }

    /**
     * @return car information
     */
    public CarContOBJ getC() {
        return c;
    }
}
