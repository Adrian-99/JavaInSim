package pl.adrian.api.packets;

import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.InstructionPacket;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.enums.TtcSubtype;
import pl.adrian.internal.packets.exceptions.PacketValidationException;
import pl.adrian.internal.packets.util.PacketBuilder;
import pl.adrian.internal.packets.util.PacketValidator;

/**
 * General purpose 8 byte packet (Target To Connection).
 */
public class TtcPacket extends Packet implements InstructionPacket {
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

    /**
     * Creates ttc packet.
     * @param subT subtype
     * @param ucid connection's unique id (0 = local)
     * @param b1 may be used in various ways depending on SubT
     * @param b2 may be used in various ways depending on SubT
     * @param b3 may be used in various ways depending on SubT
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public TtcPacket(TtcSubtype subT, int ucid, int b1, int b2, int b3) throws PacketValidationException {
        this(subT, ucid, b1, b2, b3, 0);
    }

    /**
     * Creates ttc packet.
     * @param subT subtype
     * @param ucid connection's unique id (0 = local)
     * @param b1 may be used in various ways depending on SubT
     * @param b2 may be used in various ways depending on SubT
     * @param b3 may be used in various ways depending on SubT
     * @param reqI 0 unless it is an info request or a reply to an info request
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public TtcPacket(TtcSubtype subT, int ucid, int b1, int b2, int b3, int reqI) throws PacketValidationException {
        super(8, PacketType.TTC, reqI);
        this.subT = subT;
        this.ucid = (short) ucid;
        this.b1 = (short) b1;
        this.b2 = (short) b2;
        this.b3 = (short) b3;
        PacketValidator.validate(this);
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
