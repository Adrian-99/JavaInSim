package pl.adrian.api.insim.packets;

import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.api.common.enums.DefaultCar;
import pl.adrian.api.common.flags.Flags;
import pl.adrian.internal.insim.packets.annotations.Byte;
import pl.adrian.internal.insim.packets.annotations.Unsigned;
import pl.adrian.internal.insim.packets.base.Packet;
import pl.adrian.internal.insim.packets.base.InstructionPacket;
import pl.adrian.internal.insim.packets.exceptions.PacketValidationException;
import pl.adrian.internal.insim.packets.util.PacketBuilder;
import pl.adrian.internal.insim.packets.util.PacketValidator;

/**
 * PLayer Cars. Allows limiting the cars that can be used by a given connection.
 * The resulting set of selectable cars is a subset of the cars set to be available
 * on the host (by the /cars command or {@link pl.adrian.api.insim.packets.enums.SmallSubtype#ALC Small ALC}).
 */
public class PlcPacket extends Packet implements InstructionPacket {
    @Byte
    private final short ucid;
    @Unsigned
    private final Flags<DefaultCar> cars;

    /**
     * Creates player cars packet.
     * @param ucid connection's unique id (0 = host / 255 = all)
     * @param cars allowed cars
     * @throws PacketValidationException if validation of any field in packet fails
     */
    public PlcPacket(int ucid, Flags<DefaultCar> cars) throws PacketValidationException {
        super(12, PacketType.PLC, 0);
        this.ucid = (short) ucid;
        this.cars = cars;
        PacketValidator.validate(this);
    }

    @Override
    public byte[] getBytes() {
        return new PacketBuilder(size, type, reqI)
                .writeZeroByte()
                .writeByte(ucid)
                .writeZeroBytes(3)
                .writeUnsigned(cars.getUnsignedValue())
                .getBytes();
    }
}
