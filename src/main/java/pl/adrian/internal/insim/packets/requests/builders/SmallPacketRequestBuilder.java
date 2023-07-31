package pl.adrian.internal.insim.packets.requests.builders;

import pl.adrian.api.insim.InSimConnection;
import pl.adrian.api.insim.packets.SmallPacket;
import pl.adrian.api.insim.packets.enums.TinySubtype;

/**
 * Builder for {@link SmallPacket} request.
 */
public class SmallPacketRequestBuilder {
    private final InSimConnection inSimConnection;

    /**
     * Creates builder for {@link SmallPacket} request.
     * @param inSimConnection InSim connection that packet should be requested from
     */
    public SmallPacketRequestBuilder(InSimConnection inSimConnection) {
        this.inSimConnection = inSimConnection;
    }

    /**
     * Option to choose if packet should contain the current time
     * @return packet request builder
     */
    public SingleTinyPacketRequestBuilder<SmallPacket> forCurrentTime() {
        return new SingleTinyPacketRequestBuilder<>(inSimConnection, TinySubtype.GTH);
    }

    /**
     * Option to choose if packet should contain the allowed cars
     * @return packet request builder
     */
    public SingleTinyPacketRequestBuilder<SmallPacket> forAllowedCars() {
        return new SingleTinyPacketRequestBuilder<>(inSimConnection, TinySubtype.ALC);
    }
}
