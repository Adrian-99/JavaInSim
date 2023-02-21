package pl.adrian.internal.packets.bothways;

import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.annotations.Unsigned;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.enums.SmallSubtype;

public abstract class AbstractSmallPacket extends Packet {
    @Byte
    protected SmallSubtype subT;

    @Unsigned
    protected long uVal;

    protected AbstractSmallPacket(int reqI, SmallSubtype subT, long uVal) {
        super(8, PacketType.SMALL, reqI);
        this.subT = subT;
        this.uVal = uVal;
    }

    protected AbstractSmallPacket(int reqI) {
        super(8, PacketType.SMALL, reqI);
    }
}
