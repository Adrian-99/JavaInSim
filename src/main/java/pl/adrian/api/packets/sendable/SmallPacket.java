package pl.adrian.api.packets.sendable;

import pl.adrian.api.packets.enums.SmallSubtype;
import pl.adrian.internal.packets.base.SendablePacket;
import pl.adrian.internal.packets.bothways.AbstractSmallPacket;
import pl.adrian.internal.packets.util.PacketBuilder;

public class SmallPacket extends AbstractSmallPacket implements SendablePacket {
    public SmallPacket(int reqI, SmallSubtype subT, long uVal) {
        super(reqI, subT, uVal);
    }

    @Override
    public byte[] getBytes() {
        return new PacketBuilder(size, type, reqI)
                .writeByte(subT.ordinal())
                .writeUnsigned(uVal)
                .getBytes();
    }
}
