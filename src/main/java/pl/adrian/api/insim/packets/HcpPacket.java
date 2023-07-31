package pl.adrian.api.insim.packets;

import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.api.common.enums.DefaultCar;
import pl.adrian.api.insim.packets.structures.CarHandicaps;
import pl.adrian.internal.insim.packets.annotations.Array;
import pl.adrian.internal.insim.packets.annotations.Structure;
import pl.adrian.internal.insim.packets.base.AbstractPacket;
import pl.adrian.internal.insim.packets.base.InstructionPacket;
import pl.adrian.internal.common.enums.EnumHelpers;
import pl.adrian.internal.insim.packets.exceptions.PacketValidationException;
import pl.adrian.internal.insim.packets.util.PacketBuilder;
import pl.adrian.internal.insim.packets.util.PacketValidator;

import java.util.Map;

/**
 * HandiCaPs. You can send this packet to add mass and restrict the intake on each car model.
 * The same restriction applies to all drivers using a particular car model.
 * This can be useful for creating multi class hosts.
 */
public class HcpPacket extends AbstractPacket implements InstructionPacket {
    @Structure
    @Array(length = 32)
    private final CarHandicaps[] info;

    /**
     * Creates handicaps packet.
     * @param info map containing handicaps for {@link DefaultCar default LFS cars}.
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public HcpPacket(Map<DefaultCar, CarHandicaps> info) throws PacketValidationException {
        super(68, PacketType.HCP, 0);
        this.info = new CarHandicaps[32];
        for (var car : EnumHelpers.get(DefaultCar.class).getAllValuesCached()) {
            if (info.containsKey(car)) {
                this.info[car.ordinal()] = info.get(car);
            }
        }
        PacketValidator.validate(this);
    }

    @Override
    public byte[] getBytes() {
        return new PacketBuilder(size, type, reqI)
                .writeZeroByte()
                .writeStructureArray(info, 2)
                .getBytes();
    }
}
