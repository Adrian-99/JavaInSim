package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.annotations.Array;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.annotations.Char;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.RequestablePacket;
import pl.adrian.internal.packets.util.PacketDataBytes;

/**
 * InSim Multi. The packet is sent by LFS when a host is started or joined.
 */
public class IsmPacket extends Packet implements RequestablePacket {
    @Byte
    private final boolean isHost;
    @Char
    @Array(length = 32)
    private final String hName;

    /**
     * Creates InSim multi packet. Constructor used only internally.
     * @param reqI usually 0 / or if a reply: ReqI as received in the {@link pl.adrian.api.packets.enums.TinySubtype#ISM Tiny ISM}
     * @param packetDataBytes packet data bytes
     */
    public IsmPacket(short reqI, PacketDataBytes packetDataBytes) {
        super(40, PacketType.ISM, reqI);
        packetDataBytes.skipZeroByte();
        isHost = packetDataBytes.readByte() != 0;
        packetDataBytes.skipZeroBytes(3);
        hName = packetDataBytes.readCharArray(32);
    }

    /**
     * @return whether is a host
     */
    public boolean isHost() {
        return isHost;
    }

    /**
     * @return the name of the host joined or started
     */
    public String getHName() {
        return hName;
    }
}
