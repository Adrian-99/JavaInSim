package pl.adrian.internal.packets.base;

import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.api.packets.enums.PacketType;

/**
 * This class is a foundation, on which all packet classes are built.
 * It contains packet header fields, which are included in all packets.
 */
public abstract class Packet {
    /**
     * Total packet size - a multiple of 4
     */
    @Byte
    protected final short size;
    /**
     * Packet identifier
     */
    @Byte
    protected final PacketType type;
    /**
     * Non-zero if the packet is a packet request or a reply to a request
     */
    @Byte
    protected final short reqI;

    /**
     * Initializes packet header fields
     * @param size total packet size - a multiple of 4
     * @param type packet identifier
     * @param reqI non-zero if the packet is a packet request or a reply to a request
     */
    protected Packet(int size, PacketType type, int reqI) {
        this.size = (short) size;
        this.type = type;
        this.reqI = (short) reqI;
    }

    /**
     * @return total packet size - a multiple of 4
     */
    public short getSize() {
        return size;
    }

    /**
     * @return packet identifier
     */
    public PacketType getType() {
        return type;
    }

    /**
     * @return non-zero if the packet is a packet request or a reply to a request
     */
    public short getReqI() {
        return reqI;
    }
}
