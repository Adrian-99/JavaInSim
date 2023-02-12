package pl.adrian.packets;

import pl.adrian.packets.annotations.Byte;
import pl.adrian.packets.annotations.CharArray;
import pl.adrian.packets.base.Packet;
import pl.adrian.packets.base.ReadablePacket;
import pl.adrian.packets.enums.PacketType;
import pl.adrian.packets.enums.Product;
import pl.adrian.packets.util.PacketReader;

public class IS_VER extends Packet implements ReadablePacket {

    @CharArray(maxLength = 8)
    private String version;
    @CharArray(maxLength = 6)
    private Product product;
    @Byte
    private short inSimVer;

    public IS_VER(int reqI) {
        super(20, PacketType.ISP_VER, reqI);
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
