package pl.adrian.api.insim.packets;

import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.api.insim.packets.enums.SmallSubtype;
import pl.adrian.api.insim.packets.enums.VoteAction;
import pl.adrian.api.common.enums.DefaultCar;
import pl.adrian.api.common.flags.Flags;
import pl.adrian.api.insim.packets.flags.LcsFlag;
import pl.adrian.internal.insim.packets.annotations.Byte;
import pl.adrian.internal.insim.packets.annotations.Unsigned;
import pl.adrian.internal.insim.packets.base.Packet;
import pl.adrian.internal.insim.packets.base.InfoPacket;
import pl.adrian.internal.insim.packets.base.InstructionPacket;
import pl.adrian.internal.insim.packets.exceptions.PacketValidationException;
import pl.adrian.internal.insim.packets.util.PacketBuilder;
import pl.adrian.internal.common.util.PacketDataBytes;
import pl.adrian.internal.insim.packets.util.PacketValidator;

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
        super(8, PacketType.SMALL, 0);
        this.subT = subT;
        this.uVal = uVal;
        PacketValidator.validate(this);
    }

    /**
     * Creates small packet. Constructor used only internally.
     * @param reqI 0 unless it is an info request or a reply to an info request
     * @param packetDataBytes packet data bytes
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public SmallPacket(short reqI, PacketDataBytes packetDataBytes) throws PacketValidationException {
        super(8, PacketType.SMALL, reqI);
        subT = SmallSubtype.fromOrdinal(packetDataBytes.readByte());
        uVal = packetDataBytes.readUnsigned();
    }

    /**
     * Creates small packet for local car switches.
     * @param lcsFlags local car switches
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public SmallPacket(LcsFlag... lcsFlags) throws PacketValidationException {
        this(SmallSubtype.LCS, new Flags<>(lcsFlags).getUnsignedValue());
    }

    /**
     * Creates small packet for setting allowed cars on host.
     * @param cars allowed cars
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public SmallPacket(DefaultCar... cars) throws PacketValidationException {
        this(SmallSubtype.ALC, new Flags<>(cars).getUnsignedValue());
    }

    /**
     * Creates small packet to change rate of {@link NlpPacket} or {@link MciPacket} after initialisation.
     * @param interval 0 means stop, otherwise time interval: 40, 50, 60... 8000 ms
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public SmallPacket(long interval) throws PacketValidationException {
        this(SmallSubtype.NLI, interval);
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
