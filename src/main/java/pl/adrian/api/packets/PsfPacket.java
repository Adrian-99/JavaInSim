package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.annotations.Unsigned;
import pl.adrian.internal.packets.base.InfoPacket;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.util.PacketDataBytes;

/**
 * Pit Stop Finished. The packet is sent by LFS when any player finishes their pit stop.
 */
public class PsfPacket extends Packet implements InfoPacket {
    @Byte
    private final short plid;
    @Unsigned
    private final long sTime;

    /**
     * Creates pit stop finished packet. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public PsfPacket(PacketDataBytes packetDataBytes) {
        super(12, PacketType.PSF, 0);
        plid = packetDataBytes.readByte();
        sTime = packetDataBytes.readUnsigned();
        packetDataBytes.skipZeroBytes(4);
    }

    /**
     * @return player's unique id
     */
    public short getPlid() {
        return plid;
    }

    /**
     * @return stop time (ms)
     */
    public long getSTime() {
        return sTime;
    }
}
