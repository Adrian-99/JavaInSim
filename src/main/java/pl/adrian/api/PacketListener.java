package pl.adrian.api;

import pl.adrian.internal.packets.base.InfoPacket;

/**
 * This interface allows to create packet listener - the function that will be called each time
 * packet of specified type will be received from LFS. Packet listener can be registered and unregistered
 * using {@link InSimConnection#listen(Class, PacketListener) listen} and
 * {@link InSimConnection#stopListening(Class, PacketListener) stopListening} methods respectively.
 * @param <T> type of the packet that will be handled by this packet listener
 */
@FunctionalInterface
public interface PacketListener<T extends InfoPacket> {
    /**
     * Method that will be called each time packet of specified type will be received from LFS.
     * @param inSimConnection InSim connection that triggered the listener
     * @param packet received packet
     */
    void onPacketReceived(InSimConnection inSimConnection, T packet);
}
