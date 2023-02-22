package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.enums.SmallSubtype;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.annotations.Unsigned;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.ReadablePacket;
import pl.adrian.internal.packets.base.SendablePacket;
import pl.adrian.internal.packets.exceptions.PacketValidationException;
import pl.adrian.internal.packets.util.PacketBuilder;
import pl.adrian.internal.packets.util.PacketValidator;

/**
 * General purpose 8 byte packet
 */
public class SmallPacket extends Packet implements SendablePacket, ReadablePacket {
    @Byte
    private final SmallSubtype subT;
    @Unsigned
    private final long uVal;

    /**
     * Creates small packet
     * @param reqI 0 unless it is an info request or a reply to an info request
     * @param subT subtype
     * @param uVal value
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public SmallPacket(int reqI, SmallSubtype subT, long uVal) throws PacketValidationException {
        super(8, PacketType.SMALL, reqI);
        this.subT = subT;
        this.uVal = uVal;
        PacketValidator.validate(this);
    }

    @Override
    public byte[] getBytes() {
        return new PacketBuilder(size, type, reqI)
                .writeByte(subT.ordinal())
                .writeUnsigned(uVal)
                .getBytes();
    }

    /**
     * @return subtype
     */
    public SmallSubtype getSubT() {
        return subT;
    }

    /**
     * @return value
     */
    public long getUVal() {
        return uVal;
    }
}
