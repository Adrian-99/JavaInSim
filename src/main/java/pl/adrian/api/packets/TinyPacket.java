package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.enums.TinySubtype;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.InfoPacket;
import pl.adrian.internal.packets.base.InstructionPacket;
import pl.adrian.internal.packets.exceptions.PacketValidationException;
import pl.adrian.internal.packets.util.PacketBuilder;
import pl.adrian.internal.packets.util.PacketValidator;

/**
 * General purpose 4 byte packet.
 */
public class TinyPacket extends Packet implements InstructionPacket, InfoPacket {
    @Byte
    private final TinySubtype subT;

    /**
     * Creates tiny packet.
     * @param subT subtype
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public TinyPacket(TinySubtype subT) throws PacketValidationException {
        this(subT, 0);
    }

    /**
     * Creates tiny packet.
     * @param subT subtype
     * @param reqI 0 unless it is an info request or a reply to an info request
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public TinyPacket(TinySubtype subT, int reqI) throws PacketValidationException {
        super(4, PacketType.TINY, reqI);
        this.subT = subT;
        PacketValidator.validate(this);
    }

    @Override
    public byte[] getBytes() {
        return new PacketBuilder(size, type, reqI)
                .writeByte(subT.ordinal())
                .getBytes();
    }

    /**
     * @return subtype
     */
    public TinySubtype getSubT() {
        return subT;
    }
}
