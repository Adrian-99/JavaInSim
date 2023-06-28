package pl.adrian.api.outsim.structures;

import pl.adrian.internal.packets.util.PacketDataBytes;

/**
 * Structure for header.
 */
public class OutSimHeader {
    /**
     * Size of the structure.
     */
    public static final int SIZE = 4;

    private final byte l;
    private final byte f;
    private final byte s;
    private final byte t;

    /**
     * Creates structure for header. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public OutSimHeader(PacketDataBytes packetDataBytes) {
        l = packetDataBytes.readChar();
        f = packetDataBytes.readChar();
        s = packetDataBytes.readChar();
        t = packetDataBytes.readChar();
    }

    /**
     * @return L
     */
    public byte getL() {
        return l;
    }

    /**
     * @return F
     */
    public byte getF() {
        return f;
    }

    /**
     * @return S
     */
    public byte getS() {
        return s;
    }

    /**
     * @return T
     */
    public byte getT() {
        return t;
    }
}
