package pl.adrian.internal.insim.packets.requests;

import pl.adrian.api.insim.InSimConnection;
import pl.adrian.api.insim.packets.enums.PacketType;
import pl.adrian.internal.insim.packets.base.InfoPacket;
import pl.adrian.internal.insim.packets.base.InstructionPacket;

/**
 * This interface should be implemented by all packet request implementations.
 */
public interface PacketRequest {
    /**
     * Assigns free reqI value to the packet request.
     * @param reqI reqI value
     */
    void assignReqI(short reqI);

    /**
     * @return type of the requested packet
     */
    PacketType getRequestedPacketType();

    /**
     * @return {@link InstructionPacket} that serves as a request
     */
    InstructionPacket getRequestPacket();

    /**
     * Tries to handle received {@link InfoPacket}.
     * @param inSimConnection InSim connection that packet was received from
     * @param receivedPacket received packet
     * @return whether packet request should now be removed (expected single packet response and received packet matched)
     */
    boolean handleReceivedPacket(InSimConnection inSimConnection, InfoPacket receivedPacket);

    /**
     * Checks whether provided packet type and reqI value match with information stored in this packet request.
     * @param packetType packet type
     * @param reqI reqI value
     * @return whether is matching
     */
    boolean matches(PacketType packetType, short reqI);

    /**
     * @return whether packet request is timed out
     */
    boolean isTimedOut();
}
