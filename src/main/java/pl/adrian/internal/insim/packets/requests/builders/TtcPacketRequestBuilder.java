package pl.adrian.internal.insim.packets.requests.builders;

import pl.adrian.api.insim.InSimConnection;
import pl.adrian.api.insim.PacketListener;
import pl.adrian.api.insim.packets.AxmPacket;
import pl.adrian.api.insim.packets.TtcPacket;
import pl.adrian.api.insim.packets.requests.TtcPacketRequest;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * Builder of packet request where {@link TtcPacket} serves as a request. Currently only {@link AxmPacket}
 * can be requested this way.
 */
public class TtcPacketRequestBuilder {
    private final int ucid;
    /**
     * InSim connection to request packet from
     */
    protected final InSimConnection inSimConnection;
    /**
     * timeout (in milliseconds) of the packet request - can be overridden by inheritors
     */
    protected long requestTimeout = 5000;

    /**
     * Creates packet request builder.
     * @param inSimConnection InSim connection to request packet from
     * @param ucid unique connection id (0 = local / non-zero = guest)
     */
    public TtcPacketRequestBuilder(InSimConnection inSimConnection, int ucid) {
        this.inSimConnection = inSimConnection;
        this.ucid = ucid;
    }

    /**
     * Concludes building packet request, which is then registered in provided InSim connection.
     * That causes sending appropriate {@link TtcPacket} that serves as a request.
     * @param callback method to be called when requested packet is received
     * @throws IOException if I/O error occurs when sending request packet
     */
    public void listen(PacketListener<AxmPacket> callback) throws IOException {
        inSimConnection.request(new TtcPacketRequest(ucid, callback, requestTimeout));
    }

    /**
     * Concludes building packet request, which is then registered in provided InSim connection.
     * That causes sending appropriate {@link TtcPacket} that serves as a request.
     * @return {@link CompletableFuture} that will complete with requested packet value when it is received
     * @throws IOException if I/O error occurs when sending request packet
     */
    public CompletableFuture<AxmPacket> asCompletableFuture() throws IOException {
        var completableFuture = new CompletableFuture<AxmPacket>();
        listen((connection, packet) -> completableFuture.complete(packet));
        return completableFuture;
    }
}
