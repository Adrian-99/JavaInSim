package pl.adrian.internal.insim.packets.base;

/**
 * This interface must be implemented by all packet classes that represent packets
 * which can be requested from LFS by sending appropriate {@link pl.adrian.api.insim.packets.TinyPacket TinyPacket}.
 * This interface extends {@link InfoPacket} interface, therefore it is not necessary to implement
 * both of them while creating packet class.
 */
public interface RequestablePacket extends InfoPacket {
}
