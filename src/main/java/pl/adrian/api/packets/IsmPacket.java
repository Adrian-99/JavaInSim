package pl.adrian.api.packets;

import pl.adrian.api.packets.enums.PacketType;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.annotations.CharArray;
import pl.adrian.internal.packets.base.Packet;
import pl.adrian.internal.packets.base.RequestablePacket;

/**
 * InSim Multi.
 */
public class IsmPacket extends Packet implements RequestablePacket {
    @Byte
    private final boolean isHost;
    @CharArray(length = 32)
    private final String hName;

    /**
     * Creates InSim multi packet.
     * @param reqI usually 0 / or if a reply: ReqI as received in the TINY_ISM
     * @param isHost whether is a host
     * @param hName the name of the host joined or started
     */
    public IsmPacket(short reqI, boolean isHost, String hName) {
        super(40, PacketType.ISM, reqI);
        this.isHost = isHost;
        this.hName = hName;
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
