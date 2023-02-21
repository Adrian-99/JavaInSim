package pl.adrian.api.packets;

import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.annotations.CharArray;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.ReadablePacket;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.api.packets.enums.Product;

public class VerPacket extends Packet implements ReadablePacket {
    @CharArray(length = 8)
    private final String version;
    @CharArray(length = 6)
    private final Product product;
    @Byte
    private final short inSimVer;

    public VerPacket(int reqI, String version, Product product, int inSimVer) {
        super(20, PacketType.VER, reqI);
        this.version = version;
        this.product = product;
        this.inSimVer = (short) inSimVer;
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
