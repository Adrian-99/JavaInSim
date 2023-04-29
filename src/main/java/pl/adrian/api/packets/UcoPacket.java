package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.enums.UcoAction;
import pl.adrian.api.packets.structures.CarContOBJ;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.annotations.Structure;
import pl.adrian.internal.packets.annotations.Unsigned;
import pl.adrian.internal.packets.base.InfoPacket;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.api.packets.structures.objectinfo.ObjectInfo;
import pl.adrian.internal.packets.util.PacketDataBytes;

/**
 * User Control Object. The packet is sent when any player crosses InSim checkpoint
 * or enters InSim circle (from layout).
 */
public class UcoPacket extends Packet implements InfoPacket {
    @Byte
    private final short plid;
    @Byte
    private final UcoAction ucoAction;
    @Unsigned
    private final long time;
    @Structure
    private final CarContOBJ c;
    @Structure
    private final ObjectInfo info;

    /**
     * Creates user control object packet. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public UcoPacket(PacketDataBytes packetDataBytes) {
        super(28, PacketType.UCO, 0);
        plid = packetDataBytes.readByte();
        packetDataBytes.skipZeroByte();
        ucoAction = UcoAction.fromOrdinal(packetDataBytes.readByte());
        packetDataBytes.skipZeroBytes(2);
        time = packetDataBytes.readUnsigned();
        c = new CarContOBJ(packetDataBytes);
        info = ObjectInfo.read(packetDataBytes);
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
    public UcoAction getUcoAction() {
        return ucoAction;
    }

    /**
     * @return hundredths of a second since start (as in {@link pl.adrian.api.packets.enums.SmallSubtype#RTP Small RTP})
     */
    public long getTime() {
        return time;
    }

    /**
     * @return car in contact with object information
     */
    public CarContOBJ getC() {
        return c;
    }

    /**
     * @return info about the checkpoint or circle
     */
    public ObjectInfo getInfo() {
        return info;
    }
}
