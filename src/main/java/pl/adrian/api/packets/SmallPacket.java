package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.enums.SmallSubtype;
import pl.adrian.api.packets.enums.VoteAction;
import pl.adrian.api.packets.flags.Flags;
import pl.adrian.api.packets.flags.LcsFlag;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.annotations.Unsigned;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.ReadablePacket;
import pl.adrian.internal.packets.base.SendablePacket;
import pl.adrian.internal.packets.exceptions.PacketValidationException;
import pl.adrian.internal.packets.util.PacketBuilder;
import pl.adrian.internal.packets.util.PacketValidator;

import java.util.Optional;

/**
 * General purpose 8 byte packet.
 */
public class SmallPacket extends Packet implements SendablePacket, ReadablePacket {
    @Byte
    private final SmallSubtype subT;
    @Unsigned
    private final long uVal;

    /**
     * Creates small packet.
     * @param subT subtype
     * @param uVal value
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public SmallPacket(SmallSubtype subT, long uVal) throws PacketValidationException {
        this(subT, uVal, 0);
    }

    /**
     * Creates small packet.
     * @param subT subtype
     * @param uVal value
     * @param reqI 0 unless it is an info request or a reply to an info request
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public SmallPacket(SmallSubtype subT, long uVal, int reqI) throws PacketValidationException {
        super(8, PacketType.SMALL, reqI);
        this.subT = subT;
        this.uVal = uVal;
        PacketValidator.validate(this);
    }

    /**
     * Creates small packet for local car switches.
     * @param lcsFlags local car switches flags
     */
    public SmallPacket(Flags<LcsFlag> lcsFlags) {
        this(SmallSubtype.LCS, lcsFlags.getValue(), 0);
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

    /**
     * Warning! Method should be used only if {@link #getSubT() subtype}
     * is equal to VTA.
     * @return vote action, or empty optional if subtype is not equal to VTA
     */
    public Optional<VoteAction> getVoteAction() {
        if (subT.equals(SmallSubtype.VTA)) {
            return Optional.of(VoteAction.fromOrdinal((int) uVal));
        } else {
            return Optional.empty();
        }
    }
}
