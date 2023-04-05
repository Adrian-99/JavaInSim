package pl.adrian.api.packets;

import pl.adrian.internal.packets.annotations.Array;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.annotations.Char;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.enums.Product;
import pl.adrian.internal.packets.base.RequestablePacket;
import pl.adrian.internal.packets.util.PacketDataBytes;

/**
 * VERsion - version info. This version packet is sent on request.
 */
public class VerPacket extends Packet implements RequestablePacket {
    @Char
    @Array(length = 8)
    private final String version;
    @Char
    @Array(length = 6)
    private final Product product;
    @Byte
    private final short inSimVer;

    /**
     * Creates version packet.
     * @param reqI as received in the request packet
     * @param packetDataBytes packet data bytes
     */
    public VerPacket(short reqI, PacketDataBytes packetDataBytes) {
        super(20, PacketType.VER, reqI);
        packetDataBytes.skipZeroByte();
        version = packetDataBytes.readCharArray(8);
        product = Product.fromString(packetDataBytes.readCharArray(6));
        inSimVer = packetDataBytes.readByte();
    }

    /**
     * @return LFS version, e.g. 0.3G
     */
    public String getVersion() {
        return version;
    }

    /**
     * @return LFS product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * @return InSim version
     */
    public short getInSimVer() {
        return inSimVer;
    }
}
