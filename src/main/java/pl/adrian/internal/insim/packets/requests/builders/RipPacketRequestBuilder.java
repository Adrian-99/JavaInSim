package pl.adrian.internal.insim.packets.requests.builders;

import pl.adrian.api.insim.InSimConnection;
import pl.adrian.api.insim.PacketListener;
import pl.adrian.api.insim.packets.RipPacket;
import pl.adrian.api.insim.packets.enums.TinySubtype;
import pl.adrian.api.insim.packets.requests.RipPacketRequest;

import java.io.IOException;

/**
 * Builder of packet request where {@link RipPacket} serves as a request. Only {@link RipPacket}
 * can be requested this way.
 */
public class RipPacketRequestBuilder extends SingleTinyPacketRequestBuilder<RipPacket> {
    private RipPacket ripPacket;

    /**
     * Creates builder for {@link RipPacket} request.
     * @param inSimConnection InSim connection that packet should be requested from
     */
    public RipPacketRequestBuilder(InSimConnection inSimConnection) {
        super(inSimConnection, TinySubtype.RIP);
        ripPacket = null;
        requestTimeoutMillis = 300000;
    }

    /**
     * Changes request timeout (default 30000 ms)
     * @param requestTimeout request timeout in milliseconds
     * @return packet builder
     */
    public RipPacketRequestBuilder setRequestTimeout(long requestTimeout) {
        requestTimeoutMillis = requestTimeout;
        return this;
    }

    /**
     * Option to choose if requested packet should be confirmation that request was completed.
     * @param ripPacket packet that serves as a request
     * @return packet request builder
     */
    public RipPacketRequestBuilder asConfirmationFor(RipPacket ripPacket) {
        this.ripPacket = ripPacket;
        return this;
    }

    @Override
    public void listen(PacketListener<RipPacket> callback) throws IOException {
        if (ripPacket != null) {
            inSimConnection.request(new RipPacketRequest(ripPacket, callback, requestTimeoutMillis));
        } else {
            super.listen(callback);
        }
    }
}
