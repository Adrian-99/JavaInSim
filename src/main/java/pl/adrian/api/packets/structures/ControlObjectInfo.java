package pl.adrian.api.packets.structures;

import pl.adrian.api.packets.enums.ControlObjectType;
import pl.adrian.api.packets.enums.ObjectType;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.util.PacketBuilder;

/**
 * This class hold information about control object.
 */
public class ControlObjectInfo implements ObjectSubtypeInfo {
    @Byte
    private final short flags;
    @Byte
    private final ObjectType index;
    @Byte
    private final short heading;

    ControlObjectInfo(short flags, short index, short heading) {
        this.flags = flags;
        this.index = ObjectType.fromOrdinal(index);
        this.heading = heading;
    }

    /**
     * Creates control object information.
     * @param type type of control object
     * @param width half width in metres (value ignored for {@link ControlObjectType#START_POSITION})
     * @param index object index
     * @param heading heading
     */
    public ControlObjectInfo(ControlObjectType type, int width, ObjectType index, int heading) {
        if (!type.equals(ControlObjectType.START_POSITION)) {
            flags = (short) (0x80 | type.ordinal() | (width & 31) << 2);
        } else {
            flags = 0x80;
        }
        this.index = index;
        this.heading = (short) heading;
    }

    /**
     * @return type of control object
     */
    public ControlObjectType getType() {
        var typeOrdinal = flags & 3;
        if (getWidth() > 0 || typeOrdinal > 0) {
            return ControlObjectType.fromOrdinal(typeOrdinal);
        } else {
            return ControlObjectType.START_POSITION;
        }
    }

    /**
     * @return half width in metres
     */
    public byte getWidth() {
        return (byte) ((flags >> 2) & 31);
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
