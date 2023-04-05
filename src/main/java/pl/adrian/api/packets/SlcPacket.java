package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.structures.Car;
import pl.adrian.internal.packets.annotations.Array;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.annotations.Char;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.RequestablePacket;
import pl.adrian.internal.packets.util.PacketDataBytes;

/**
 * SeLected Car. The packet is sent by LFS when a connection selects a car (empty if no car).
 * NOTE: If a new guest joins and does have a car selected then this packet will be sent.
 */
public class SlcPacket extends Packet implements RequestablePacket {
    @Byte
    private final short ucid;

    @Char
    @Array(length = 4)
    private final Car car;

    /**
     * Creates selected car packet. Constructor used only internally.
     * @param reqI 0 unless this is a reply to a {@link pl.adrian.api.packets.enums.TinySubtype#SLC Tiny SLC} request
     * @param packetDataBytes packet data bytes
     */
    public SlcPacket(short reqI, PacketDataBytes packetDataBytes) {
        super(8, PacketType.SLC, reqI);
        ucid = packetDataBytes.readByte();
        car = new Car(packetDataBytes.readByteArray(4));
    }

    /**
     * @return connection's unique id (0 = host)
     */
    public short getUcid() {
        return ucid;
    }

    /**
     * @return selected car
     */
    public Car getCar() {
        return car;
    }
}
