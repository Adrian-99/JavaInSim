package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.AcrResult;
import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.annotations.Array;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.annotations.Char;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.InfoPacket;
import pl.adrian.internal.packets.util.PacketDataBytes;

/**
 * Admin Command Report - a user typed an admin command - variable size.
 */
public class AcrPacket extends Packet implements InfoPacket {
    @Byte
    private final short ucid;
    @Byte
    private final boolean isAdmin;
    @Byte
    private final AcrResult result;
    @Char
    @Array(length = 64)
    private final String text;

    /**
     * Creates admin command report packet. Constructor used only internally.
     * @param size packet size
     * @param packetDataBytes packet data bytes
     */
    public AcrPacket(short size, PacketDataBytes packetDataBytes) {
        super(size, PacketType.ACR, 0);
        packetDataBytes.skipZeroByte();
        ucid = packetDataBytes.readByte();
        isAdmin = packetDataBytes.readByte() != 0;
        result = AcrResult.fromOrdinal(packetDataBytes.readByte());
        packetDataBytes.skipZeroByte();
        text = packetDataBytes.readCharArray(size - 8);
    }

    /**
     * @return connection's unique id (0 = host)
     */
    public short getUcid() {
        return ucid;
    }

    /**
     * @return true if user is an admin
     */
    public boolean isAdmin() {
        return isAdmin;
    }

    /**
     * @return command result
     */
    public AcrResult getResult() {
        return result;
    }

    /**
     * @return command text
     */
    public String getText() {
        return text;
    }
}
