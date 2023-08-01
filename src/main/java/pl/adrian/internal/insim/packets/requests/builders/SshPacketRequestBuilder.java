package pl.adrian.internal.insim.packets.requests.builders;

import pl.adrian.api.insim.InSimConnection;
import pl.adrian.api.insim.PacketListener;
import pl.adrian.api.insim.packets.SshPacket;
import pl.adrian.api.insim.packets.requests.SshPacketRequest;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * Builder of packet request where {@link SshPacket} serves as a request. Only {@link SshPacket}
 * can be requested this way.
 */
public class SshPacketRequestBuilder {
    private final InSimConnection inSimConnection;
    private final SshPacket requestPacket;

    /**
     * Creates builder for {@link SshPacket} request.
     * @param inSimConnection InSim connection that packet should be requested from
     * @param requestPacket packet that serves as a request
     */
    public SshPacketRequestBuilder(InSimConnection inSimConnection, SshPacket requestPacket) {
        this.inSimConnection = inSimConnection;
        this.requestPacket = requestPacket;
    }

    /**
     * Concludes building packet request, which is then registered in provided InSim connection.
     * That causes sending appropriate {@link SshPacket} that serves as a request.
     * @param callback method to be called when requested packet is received
     * @throws IOException if I/O error occurs when sending request packet
     */
    public void listen(PacketListener<SshPacket> callback) throws IOException {
        inSimConnection.request(new SshPacketRequest(requestPacket, callback, 5000));
    }

    /**
     * Concludes building packet request, which is then registered in provided InSim connection.
     * That causes sending appropriate {@link SshPacket} that serves as a request.
     * @return {@link CompletableFuture} that will complete with requested packet value when it is received
     * @throws IOException if I/O error occurs when sending request packet
     */
    public CompletableFuture<SshPacket> asCompletableFuture() throws IOException {
        var completableFuture = new CompletableFuture<SshPacket>();
        listen((connection, packet) -> completableFuture.complete(packet));
        return completableFuture;
    }
}
