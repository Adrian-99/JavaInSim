package pl.adrian.packets;

import pl.adrian.packets.annotations.Byte;
import pl.adrian.packets.base.Packet;
import pl.adrian.packets.base.SendablePacket;
import pl.adrian.packets.enums.PacketType;
import pl.adrian.packets.enums.TtcSubtype;
import pl.adrian.packets.util.PacketBuilder;

public class IS_TTC extends Packet implements SendablePacket {

    @Byte
    private final TtcSubtype subT;

    @Byte
    private final short ucid;

    @Byte
    private final short b1;

    @Byte
    private final short b2;

    @Byte
    private final short b3;

    public IS_TTC(int reqI, TtcSubtype subT, int ucid, int b1, int b2, int b3) {
        super(8, PacketType.ISP_TTC, reqI);
        this.subT = subT;
        this.ucid = (short) ucid;
        this.b1 = (short) b1;
        this.b2 = (short) b2;
        this.b3 = (short) b3;
    }

    @Override
    public byte[] getBytes() {
        return new PacketBuilder(size, type, reqI)
                .writeByte(subT.ordinal())
                .writeByte(ucid)
                .writeByte(b1)
                .writeByte(b2)
                .writeByte(b3)
                .getBytes();
    }
}
