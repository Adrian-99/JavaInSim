package pl.adrian.api.packets.readable;

import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.annotations.CharArray;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.ReadablePacket;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.enums.Product;
import pl.adrian.internal.packets.util.PacketReader;

public class VerPacket extends Packet implements ReadablePacket {

    @CharArray(maxLength = 8)
    private String version;
    @CharArray(maxLength = 6)
    private Product product;
    @Byte
    private short inSimVer;

    public VerPacket(int reqI) {
        super(20, PacketType.VER, reqI);
    }

    @Override
    public ReadablePacket readDataBytes(PacketReader packetReader) {
        packetReader.skipZeroByte();
        version = packetReader.readCharArray(8);
        product = Product.fromString(packetReader.readCharArray(6));
        inSimVer = (short) packetReader.readByte();

        return this;
    }

    public String getVersion() {
        return version;
    }

    public Product getProduct() {
        return product;
    }

    public short getInSimVer() {
        return inSimVer;
    }
}
