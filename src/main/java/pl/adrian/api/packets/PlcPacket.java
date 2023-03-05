package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.flags.Car;
import pl.adrian.api.packets.flags.Flags;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.annotations.Unsigned;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.SendablePacket;
import pl.adrian.internal.packets.util.PacketBuilder;

/**
 * PLayer Cars.
 */
public class PlcPacket extends Packet implements SendablePacket {
    @Byte
    private final short ucid;
    @Unsigned
    private final Flags<Car> cars;

    /**
     * Creates player cars packet.
     * @param ucid connection's unique id (0 = host / 255 = all)
     * @param cars allowed cars
     */
    public PlcPacket(int ucid, Flags<Car> cars) {
        super(12, PacketType.PLC, 0);
        this.ucid = (short) ucid;
        this.cars = cars;
    }

    @Override
    public byte[] getBytes() {
        return new PacketBuilder(size, type, reqI)
                .writeZeroByte()
                .writeByte(ucid)
                .writeZeroBytes(3)
                .writeUnsigned(cars.getValue())
                .getBytes();
    }
}
