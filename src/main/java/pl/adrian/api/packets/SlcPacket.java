package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.structures.Car;
import pl.adrian.internal.packets.annotations.Array;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.annotations.Char;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.RequestablePacket;

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
     * Creates selected car packet.
     * @param reqI 0 unless this is a reply to a {@link pl.adrian.api.packets.enums.TinySubtype#SLC Tiny SLC} request
     * @param ucid connection's unique id (0 = host)
     * @param car car
     */
    public SlcPacket(short reqI, short ucid, Car car) {
        super(8, PacketType.SLC, reqI);
        this.ucid = ucid;
        this.car = car;
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
