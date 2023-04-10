package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.Hlvc;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.structures.CarContOBJ;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.annotations.Structure;
import pl.adrian.internal.packets.annotations.Word;
import pl.adrian.internal.packets.base.InfoPacket;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.util.PacketDataBytes;

/**
 * Hot Lap Validity - reports of incidents that would violate HLVC.
 * To receive this packet set the {@link pl.adrian.api.packets.flags.IsiFlag#HLV Isi HLV} flag in the {@link IsiPacket}.
 */
public class HlvPacket extends Packet implements InfoPacket {
    @Byte
    private final short plid;
    @Byte
    private final Hlvc hlvc;
    @Word
    private final int time;
    @Structure
    private final CarContOBJ c;

    /**
     * Creates hot lap validity packet.
     * @param packetDataBytes packet data bytes
     */
    public HlvPacket(PacketDataBytes packetDataBytes) {
        super(16, PacketType.HLV, 0);
        plid = packetDataBytes.readByte();
        hlvc = Hlvc.fromOrdinal(packetDataBytes.readByte());
        packetDataBytes.skipZeroByte();
        time = packetDataBytes.readWord();
        c = new CarContOBJ(packetDataBytes);
    }

    /**
     * @return player's unique id
     */
    public short getPlid() {
        return plid;
    }

    /**
     * @return HLVC
     */
    public Hlvc getHlvc() {
        return hlvc;
    }

    /**
     * @return looping time stamp (hundredths - time since reset - like {@link pl.adrian.api.packets.enums.TinySubtype#GTH Tiny GTH})
     */
    public int getTime() {
        return time;
    }

    /**
     * @return car in contact with object information
     */
    public CarContOBJ getC() {
        return c;
    }
}
