package pl.adrian.api.insim.packets.requests;

import org.slf4j.LoggerFactory;
import pl.adrian.api.insim.PacketListener;
import pl.adrian.api.insim.packets.TinyPacket;
import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.api.insim.packets.enums.TinySubtype;
import pl.adrian.internal.insim.packets.base.InstructionPacket;
import pl.adrian.internal.insim.packets.base.RequestablePacket;
import pl.adrian.internal.insim.packets.requests.AbstractPacketRequest;

import java.util.Objects;

/**
 * Packet request where {@link TinyPacket} serves as a request packet.
 */
public class TinyPacketRequest<T extends RequestablePacket> extends AbstractPacketRequest<T> {
    private final TinySubtype tinySubtype;

    /**
     * Creates packet request.
     * @param requestedPacketClass class of the requested packet
     * @param callback method to be called when requested packet is received
     * @param timeoutMillis period of time (in milliseconds) after which packet request should be considered timed out
     */
    public TinyPacketRequest(Class<T> requestedPacketClass, PacketListener<T> callback, long timeoutMillis) {
        this(
                Objects.requireNonNull(TinySubtype.Requesting.fromRequestablePacketClass(requestedPacketClass)),
                callback,
                timeoutMillis
        );
    }

    /**
     * Creates packet request.
     * @param tinySubtype subtype to be used in request packet
     * @param callback method to be called when requested packet is received
     * @param timeoutMillis period of time (in milliseconds) after which packet request should be considered timed out
     */
    public TinyPacketRequest(TinySubtype.Requesting<T> tinySubtype, PacketListener<T> callback, long timeoutMillis) {
        super(
                LoggerFactory.getLogger(TinyPacketRequest.class),
                PacketType.fromPacketClass(tinySubtype.getRequestingPacketClass()),
                !tinySubtype.isMultiPacketResponse(),
                callback,
                timeoutMillis
        );
        this.tinySubtype = tinySubtype;
    }

    @Override
    protected InstructionPacket createRequestPacket(short reqI) {
        return new TinyPacket(reqI, tinySubtype);
    }
}
