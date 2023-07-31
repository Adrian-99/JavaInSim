package pl.adrian.internal.insim.packets.requests.builders;

import pl.adrian.api.insim.InSimConnection;
import pl.adrian.api.insim.packets.TinyPacket;
import pl.adrian.api.insim.packets.enums.TinySubtype;
import pl.adrian.internal.insim.packets.base.RequestablePacket;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * Builder of packet request where {@link TinyPacket} serves as a request. Only single packet response is allowed.
 * @param <T> type of packet to be requested
 */
public class SingleTinyPacketRequestBuilder<T extends RequestablePacket> extends BasicTinyPacketRequestBuilder<T> {
    /**
     * Creates packet request builder.
     * @param inSimConnection InSim connection to request packet from
     * @param tinySubtype subtype to be used in request packet
     */
    public SingleTinyPacketRequestBuilder(InSimConnection inSimConnection, TinySubtype.Requesting<T> tinySubtype) {
        super(inSimConnection, tinySubtype);
    }

    /**
     * Concludes building packet request, which is then registered in provided InSim connection.
     * That causes sending appropriate {@link TinyPacket} that serves as a request.
     * @return {@link CompletableFuture} that will complete with requested packet value when it is received
     * @throws IOException if I/O error occurs when sending request packet
     */
    public CompletableFuture<T> asCompletableFuture() throws IOException {
        var completableFuture = new CompletableFuture<T>();
        listen((inSimConnection, packet) -> completableFuture.complete(packet));
        return completableFuture;
    }
}
