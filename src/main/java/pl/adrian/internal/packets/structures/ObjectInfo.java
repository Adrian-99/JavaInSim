package pl.adrian.internal.packets.structures;

import pl.adrian.api.packets.structures.*;
import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.annotations.Short;
import pl.adrian.internal.packets.structures.base.ComplexInstructionStructure;
import pl.adrian.internal.packets.util.PacketBuilder;
import pl.adrian.internal.packets.util.PacketDataBytes;

import java.util.Optional;

/**
 * This class holds information about layout object.
 */
public abstract class ObjectInfo implements ComplexInstructionStructure {
    /**
     * X position (1 metre = 16)
     */
    @Short
    protected final short x;

    /**
     * Y position (1 metre = 16)
     */
    @Short
    protected final short y;

    /**
     * height (1m = 4)
     */
    @Byte
    protected final short zByte;

    /**
     * object flags
     */
    @Byte
    protected final short flags;

    /**
     * object index
     */
    @Byte
    protected final short index;

    /**
     * heading
     */
    @Byte
    protected final short heading;

    /**
     * Creates object information.
     * @param x X position (1 metre = 16)
     * @param y Y position (1 metre = 16)
     * @param zByte height (1m = 4)
     * @param flags object flags
     * @param index object index
     * @param heading heading
     */
    protected ObjectInfo(short x, short y, short zByte, short flags, short index, short heading) {
        this.x = x;
        this.y = y;
        this.zByte = zByte;
        this.flags = flags;
        this.index = index;
        this.heading = heading;
    }

    /**
     * @return X position (1 metre = 16)
     */
    public short getX() {
        return x;
    }

    /**
     * @return Y position (1 metre = 16)
     */
    public short getY() {
        return y;
    }

    /**
     * @return height (1m = 4)
     */
    public short getZByte() {
        return zByte;
    }

    /**
     * @return restricted area info, or empty if it's not restricted area
     */
    public Optional<RestrictedAreaInfo> asRestrictedAreaInfo() {
        if (this instanceof RestrictedAreaInfo restrictedAreaInfo) {
            return Optional.of(restrictedAreaInfo);
        } else {
            return Optional.empty();
        }
    }

    /**
     * @return route checker info, or empty if it's not route checker
     */
    public Optional<RouteCheckerInfo> asRouteCheckerInfo() {
        if (this instanceof RouteCheckerInfo routeCheckerInfo) {
            return Optional.of(routeCheckerInfo);
        } else {
            return Optional.empty();
        }
    }

    /**
     * @return unknown object info, or empty if it's not unknown object
     */
    public Optional<UnknownObjectInfo> asUnknownObjectInfo() {
        if (this instanceof UnknownObjectInfo unknownObjectInfo) {
            return Optional.of(unknownObjectInfo);
        } else {
            return Optional.empty();
        }
    }

    /**
     * @return control object info, or empty is it's not control object
     */
    public Optional<ControlObjectInfo> asControlObjectInfo() {
        if (this instanceof ControlObjectInfo controlObjectInfo) {
            return Optional.of(controlObjectInfo);
        } else {
            return Optional.empty();
        }
    }

    /**
     * @return autocross object info, or empty if it's not autocross object
     */
    public Optional<AutocrossObjectInfo> asAutocrossObjectInfo() {
        if (this instanceof  AutocrossObjectInfo autocrossObjectInfo) {
            return Optional.of(autocrossObjectInfo);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void appendBytes(PacketBuilder packetBuilder) {
        packetBuilder.writeShort(x)
                .writeShort(y)
                .writeByte(zByte)
                .writeByte(flags)
                .writeByte(index)
                .writeByte(heading);
    }

    /**
     * Reads appropriate object info implementation from packet data bytes.
     * @param packetDataBytes packet data bytes
     * @return read object info
     */
    public static ObjectInfo read(PacketDataBytes packetDataBytes) {
        var x = packetDataBytes.readShort();
        var y = packetDataBytes.readShort();
        var zByte = packetDataBytes.readByte();
        var flags = packetDataBytes.readByte();
        var index = packetDataBytes.readByte();
        var heading = packetDataBytes.readByte();

        if (index >= 192) {
            if (index == 255) {
                if ((flags & 0x80) == 0x80) {
                    return new RestrictedAreaInfo(x, y, zByte, flags, heading);
                } else {
                    return new RouteCheckerInfo(x, y, zByte, flags, heading);
                }
            } else {
                return new UnknownObjectInfo(x, y, zByte, flags, index, heading);
            }
        } else {
            if ((flags & 0x80) == 0x80) {
                return new ControlObjectInfo(x, y, zByte, flags, index, heading);
            } else {
                return new AutocrossObjectInfo(x, y, zByte, flags, index, heading);
            }
        }
    }
}
