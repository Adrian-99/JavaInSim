package pl.adrian.api.packets.structures;

import pl.adrian.api.packets.enums.MarshallType;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.util.PacketBuilder;

/**
 * This class hold information about restricted area.
 */
public class RestrictedAreaInfo implements ObjectSubtypeInfo {
    @Byte
    private final short flags;
    @Byte
    private final short heading;

    RestrictedAreaInfo(short flags, short heading) {
        this.flags = flags;
        this.heading = heading;
    }

    /**
     * Creates restricted area information.
     * @param marshallType marshall type
     * @param radius radius in meters
     * @param heading heading
     */
    public RestrictedAreaInfo(MarshallType marshallType, int radius, int heading) {
        flags = (short) (0x80 | marshallType.ordinal() | (radius & 31) << 2);
        this.heading = (short) heading;
    }

    /**
     * @return marshall type
     */
    public MarshallType getMarshallType() {
        return MarshallType.fromOrdinal(flags & 3);
    }

    /**
     * @return radius in meters
     */
    public byte getRadius() {
        return (byte) ((flags >> 2) & 31);
    }

    /**
     * @return heading
     */
    public short getHeading() {
        return heading;
    }

    @Override
    public void appendBytes(PacketBuilder packetBuilder) {
        packetBuilder.writeByte(flags)
                .writeByte(255)
                .writeByte(heading);
    }
}
