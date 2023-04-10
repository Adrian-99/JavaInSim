package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.structures.CarContact;
import pl.adrian.internal.packets.annotations.Structure;
import pl.adrian.internal.packets.annotations.Word;
import pl.adrian.internal.packets.base.InfoPacket;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.util.PacketDataBytes;

/**
 * CONtact - between two cars (A and B are sorted by PLID).
 * To receive this packet set the {@link pl.adrian.api.packets.flags.IsiFlag#CON Isi CON} flag in the {@link IsiPacket}.
 */
public class ConPacket extends Packet implements InfoPacket {
    @Word
    private final int spClose;
    @Word
    private final int time;
    @Structure
    private final CarContact a;
    @Structure
    private final CarContact b;

    /**
     * Creates contact packet. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public ConPacket(PacketDataBytes packetDataBytes) {
        super(40, PacketType.CON, 0);
        packetDataBytes.skipZeroByte();
        spClose = packetDataBytes.readWord();
        time = packetDataBytes.readWord();
        a = new CarContact(packetDataBytes);
        b = new CarContact(packetDataBytes);
    }

    /**
     * @return closing speed (10 = 1 m/s)
     */
    public short getClose() {
        return (short) (spClose & 0xFFF);
    }

    /**
     * @return looping time stamp (hundredths - time since reset - like {@link pl.adrian.api.packets.enums.TinySubtype#GTH Tiny GTH})
     */
    public int getTime() {
        return time;
    }

    /**
     * @return first car in contact
     */
    public CarContact getA() {
        return a;
    }

    /**
     * @return second car in contact
     */
    public CarContact getB() {
        return b;
    }
}
