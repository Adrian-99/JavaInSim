package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.annotations.Array;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.annotations.Char;
import pl.adrian.internal.packets.annotations.Word;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.RequestablePacket;
import pl.adrian.internal.packets.util.PacketDataBytes;

/**
 * AutoX Info. The packet is sent when a layout is loaded.
 */
public class AxiPacket extends Packet implements RequestablePacket {
    @Byte
    private final short aXStart;
    @Byte
    private final short numCP;
    @Word
    private final int numO;
    @Char
    @Array(length = 32)
    private final String lName;

    /**
     * Creates autoX info packet. Constructor used only internally.
     * @param reqI 0 unless this is a reply to an {@link pl.adrian.api.packets.enums.TinySubtype#AXI Tiny AXI} request
     * @param packetDataBytes packet data bytes
     */
    public AxiPacket(short reqI, PacketDataBytes packetDataBytes) {
        super(40, PacketType.AXI, reqI);
        packetDataBytes.skipZeroByte();
        aXStart = packetDataBytes.readByte();
        numCP = packetDataBytes.readByte();
        numO = packetDataBytes.readWord();
        lName = packetDataBytes.readCharArray(32);
    }

    /**
     * @return autocross start position
     */
    public short getAXStart() {
        return aXStart;
    }

    /**
     * @return number of checkpoints
     */
    public short getNumCP() {
        return numCP;
    }

    /**
     * @return number of objects
     */
    public int getNumO() {
        return numO;
    }

    /**
     * @return the name of the layout last loaded (if loaded locally)
     */
    public String getLName() {
        return lName;
    }
}
