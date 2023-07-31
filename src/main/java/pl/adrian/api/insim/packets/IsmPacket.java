package pl.adrian.api.insim.packets;

import pl.adrian.api.insim.InSimConnection;
import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.api.insim.packets.enums.TinySubtype;
import pl.adrian.internal.insim.packets.annotations.Array;
import pl.adrian.internal.insim.packets.annotations.Byte;
import pl.adrian.internal.insim.packets.annotations.Char;
import pl.adrian.internal.insim.packets.base.AbstractPacket;
import pl.adrian.internal.insim.packets.base.RequestablePacket;
import pl.adrian.internal.common.util.PacketDataBytes;
import pl.adrian.internal.insim.packets.requests.builders.SingleTinyPacketRequestBuilder;

/**
 * InSim Multi. The packet is sent by LFS when a host is started or joined.
 */
public class IsmPacket extends AbstractPacket implements RequestablePacket {
    @Byte
    private final boolean isHost;
    @Char
    @Array(length = 32)
    private final String hName;

    /**
     * Creates InSim multi packet. Constructor used only internally.
     * @param reqI usually 0 / or if a reply: ReqI as received in the {@link pl.adrian.api.insim.packets.enums.TinySubtype#ISM Tiny ISM}
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

    /**
     * Creates builder for packet request for {@link IsmPacket}.
     * @param inSimConnection InSim connection to request packet from
     * @return packet request builder
     */
    public static SingleTinyPacketRequestBuilder<IsmPacket> request(InSimConnection inSimConnection) {
        return new SingleTinyPacketRequestBuilder<>(inSimConnection, TinySubtype.ISM);
    }
}
