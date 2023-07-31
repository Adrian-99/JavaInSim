package pl.adrian.api.insim.packets;

import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.api.insim.packets.enums.TinySubtype;
import pl.adrian.internal.insim.packets.annotations.Byte;
import pl.adrian.internal.insim.packets.base.AbstractPacket;
import pl.adrian.internal.insim.packets.base.InfoPacket;
import pl.adrian.internal.insim.packets.base.InstructionPacket;
import pl.adrian.internal.insim.packets.exceptions.PacketValidationException;
import pl.adrian.internal.insim.packets.util.PacketBuilder;
import pl.adrian.internal.common.util.PacketDataBytes;
import pl.adrian.internal.insim.packets.util.PacketValidator;

/**
 * General purpose 4 byte packet.
 */
public class TinyPacket extends AbstractPacket implements InstructionPacket, InfoPacket {
    @Byte
    private final TinySubtype subT;

    /**
     * Creates tiny packet.
     * @param reqI 0 unless it is an info request or a reply to an info request
     * @param subT subtype
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public TinyPacket(int reqI, TinySubtype subT) throws PacketValidationException {
        super(4, PacketType.TINY, reqI);
        this.subT = subT;
        PacketValidator.validate(this);
    }

    /**
     * Creates tiny packet. Constructor used only internally.
     * @param reqI 0 unless it is an info request or a reply to an info request
     * @param packetDataBytes packet data bytes
     */
    public TinyPacket(short reqI, PacketDataBytes packetDataBytes) throws PacketValidationException {
        super(4, PacketType.TINY, reqI);
        subT = TinySubtype.fromOrdinal(packetDataBytes.readByte());
    }

    @Override
    public byte[] getBytes() {
        return new PacketBuilder(size, type, reqI)
                .writeByte(subT.getByteValue())
                .getBytes();
    }

    /**
     * @return subtype
     */
    public TinySubtype getSubT() {
        return subT;
    }
}
