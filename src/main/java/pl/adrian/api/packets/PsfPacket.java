package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.annotations.Unsigned;
import pl.adrian.internal.packets.base.InfoPacket;
import pl.adrian.internal.packets.base.Packet;

/**
 * Pit Stop Finished. The packet is sent by LFS when any player finishes their pit stop.
 */
public class PsfPacket extends Packet implements InfoPacket {
    @Byte
    private final short plid;
    @Unsigned
    private final long sTime;

    /**
     * Creates pit stop finished packet.
     * @param plid player's unique id
     * @param sTime stop time (ms)
     */
    public PsfPacket(short plid, long sTime) {
        super(12, PacketType.PSF, 0);
        this.plid = plid;
        this.sTime = sTime;
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
