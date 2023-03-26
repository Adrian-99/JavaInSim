package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.enums.SmallSubtype;
import pl.adrian.api.packets.enums.VoteAction;
import pl.adrian.api.packets.enums.DefaultCar;
import pl.adrian.api.packets.flags.Flags;
import pl.adrian.api.packets.flags.LcsFlag;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.annotations.Unsigned;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.InfoPacket;
import pl.adrian.internal.packets.base.InstructionPacket;
import pl.adrian.internal.packets.exceptions.PacketValidationException;
import pl.adrian.internal.packets.util.PacketBuilder;
import pl.adrian.internal.packets.util.PacketValidator;

import java.util.Optional;

/**
 * General purpose 8 byte packet.
 */
public class SmallPacket extends Packet implements InstructionPacket, InfoPacket {
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
     * @param lcsFlags local car switches
     */
    public SmallPacket(LcsFlag... lcsFlags) {
        this(SmallSubtype.LCS, new Flags<>(lcsFlags).getUnsignedValue(), 0);
    }

    /**
     * Creates small packet for setting allowed cars on host.
     * @param cars allowed cars
     */
    public SmallPacket(DefaultCar... cars) {
        this(SmallSubtype.ALC, new Flags<>(cars).getUnsignedValue(), 0);
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
     * @return vote action, or empty optional if {@link #getSubT() subtype} is
     * not equal to {@link SmallSubtype#VTA}
     */
    public Optional<VoteAction> getVoteAction() {
        if (subT.equals(SmallSubtype.VTA)) {
            return Optional.of(VoteAction.fromOrdinal((int) uVal));
        } else {
            return Optional.empty();
        }
    }

    /**
     * @return allowed cars, or empty optional if {@link #getSubT() subtype} is
     * not equal to {@link SmallSubtype#ALC}
     */
    public Optional<Flags<DefaultCar>> getCars() {
        if (subT.equals(SmallSubtype.ALC)) {
            return Optional.of(new Flags<>(DefaultCar.class, (int) uVal));
        } else {
            return Optional.empty();
        }
    }
}
