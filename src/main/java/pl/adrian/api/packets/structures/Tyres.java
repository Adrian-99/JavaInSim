package pl.adrian.api.packets.structures;

import pl.adrian.api.packets.enums.TyreCompound;
import pl.adrian.internal.packets.util.PacketDataBytes;

/**
 * This class holds information about car tyres. In {@link pl.adrian.api.packets.NplPacket NplPacket} those are
 * types of tyres that player's car is equipped with. In {@link pl.adrian.api.packets.PitPacket PitPacket} those
 * are tyres that have been changed during pit stop.
 */
public class Tyres {
    private final TyreCompound rearLeft;
    private final TyreCompound rearRight;
    private final TyreCompound frontLeft;
    private final TyreCompound frontRight;

    /**
     * Creates tyres information. Constructor used only internally.
     * @param packetDataBytes packet data bytes
     */
    public Tyres(PacketDataBytes packetDataBytes) {
        var unsignedValue = packetDataBytes.readUnsigned();
        rearLeft = TyreCompound.fromOrdinal((int) (unsignedValue & 0xFF));
        rearRight = TyreCompound.fromOrdinal((int) ((unsignedValue >> 8) & 0xFF));
        frontLeft = TyreCompound.fromOrdinal((int) ((unsignedValue >> 16) & 0xFF));
        frontRight = TyreCompound.fromOrdinal((int) ((unsignedValue >> 24) & 0xFF));
    }

    /**
     * @return compound of rear left tyre
     */
    public TyreCompound getRearLeft() {
        return rearLeft;
    }

    /**
     * @return compound of rear right tyre
     */
    public TyreCompound getRearRight() {
        return rearRight;
    }

    /**
     * @return compound of front left tyre
     */
    public TyreCompound getFrontLeft() {
        return frontLeft;
    }

    /**
     * @return compound of front right tyre
     */
    public TyreCompound getFrontRight() {
        return frontRight;
    }
}
