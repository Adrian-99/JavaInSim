package pl.adrian.api.packets.structures;

import pl.adrian.api.packets.enums.ObjectType;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.util.PacketBuilder;

/**
 * This class hold information about autocross object.
 */
public class AutocrossObjectInfo implements ObjectSubtypeInfo {
    @Byte
    private final short flags;
    @Byte
    private final ObjectType index;
    @Byte
    private final short heading;

    AutocrossObjectInfo(short flags, short index, short heading) {
        this.flags = flags;
        this.index = ObjectType.fromOrdinal(index);
        this.heading = heading;
    }

    /**
     * Creates autocross object information.
     * @param colour colour - only used for chalk (0-3) and tyres (0-5)
     * @param index object index
     * @param heading heading
     */
    public AutocrossObjectInfo(int colour, ObjectType index, int heading) {
        flags = (short) (colour & 7);
        this.index = index;
        this.heading = (short) heading;
    }

    /**
     * @return colour - only used for chalk (0-3) and tyres (0-5)
     */
    public byte getColour() {
        return (byte) (flags & 7);
    }

    /**
     * @return object index
     */
    public ObjectType getIndex() {
        return index;
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
                .writeByte(index.getValue())
                .writeByte(heading);
    }
}
