package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.flags.Car;
import pl.adrian.api.packets.structures.CarHandicaps;
import pl.adrian.internal.packets.annotations.StructureArray;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.InstructionPacket;
import pl.adrian.internal.packets.enums.EnumHelpers;
import pl.adrian.internal.packets.exceptions.PacketValidationException;
import pl.adrian.internal.packets.util.PacketBuilder;
import pl.adrian.internal.packets.util.PacketValidator;

import java.util.Map;

/**
 * HandiCaPs. You can send this packet to add mass and restrict the intake on each car model.
 * The same restriction applies to all drivers using a particular car model.
 * This can be useful for creating multi class hosts.
 */
public class HcpPacket extends Packet implements InstructionPacket {
    @StructureArray(length = 32)
    private final CarHandicaps[] info;

    /**
     * Creates handicaps packet.
     * @param info map containing handicaps for default LFS {@link Car Cars}.
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public HcpPacket(Map<Car, CarHandicaps> info) throws PacketValidationException {
        super(68, PacketType.HCP, 0);
        this.info = new CarHandicaps[32];
        for (var car : EnumHelpers.get(Car.class).getAllValuesCached()) {
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
