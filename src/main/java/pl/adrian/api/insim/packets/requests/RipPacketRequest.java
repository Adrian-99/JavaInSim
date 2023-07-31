package pl.adrian.api.insim.packets.requests;

import org.slf4j.LoggerFactory;
import pl.adrian.api.insim.PacketListener;
import pl.adrian.api.insim.packets.RipPacket;
import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.internal.insim.packets.base.InstructionPacket;
import pl.adrian.internal.insim.packets.requests.AbstractPacketRequest;

/**
 * Packet request where {@link RipPacket} serves as a request packet. Only {@link RipPacket}
 * can be requested this way.
 */
public class RipPacketRequest extends AbstractPacketRequest<RipPacket> {
    private final RipPacket requestPacket;

    /**
     * Creates packet request.
     * @param requestPacket packet that serves as a request
     * @param callback method to be called when requested packet is received
     * @param timeoutMillis period of time (in milliseconds) after which packet request should be considered timed out
     */
    public RipPacketRequest(RipPacket requestPacket, PacketListener<RipPacket> callback, long timeoutMillis) {
        super(LoggerFactory.getLogger(RipPacketRequest.class), PacketType.RIP, true, callback, timeoutMillis);
        this.requestPacket = requestPacket;
    }

    @Override
    protected InstructionPacket createRequestPacket(short reqI) {
        requestPacket.setReqI(reqI);
        return requestPacket;
    }
}
