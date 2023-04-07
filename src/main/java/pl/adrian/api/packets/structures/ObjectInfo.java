package pl.adrian.api.packets.structures;

import pl.adrian.internal.packets.annotations.Byte;
import pl.adrian.internal.packets.annotations.Short;
import pl.adrian.internal.packets.annotations.Structure;
import pl.adrian.internal.packets.structures.base.ComplexInstructionStructure;
import pl.adrian.internal.packets.util.PacketBuilder;

import java.util.Optional;

/**
 * This class holds information about layout object.
 */
public class ObjectInfo implements ComplexInstructionStructure {
    @Short
    private final short x;
    @Short
    private final short y;
    @Byte
    private final short zByte;
    @Structure
    private final ObjectSubtypeInfo objectSubtypeInfo;

    /**
     * Creates object information. Constructor used only internally.
     * @param x X position (1 metre = 16)
     * @param y Y position (1 metre = 16)
     * @param zByte height (1m = 4)
     * @param flags object flags
     * @param index object index
     * @param heading heading
     */
    public ObjectInfo(short x, short y, short zByte, short flags, short index, short heading) {
        this.x = x;
        this.y = y;
        this.zByte = zByte;

        if (index >= 192) {
            if (index == 255) {
                if ((flags & 0x80) == 0x80) {
                    objectSubtypeInfo = new RestrictedAreaInfo(flags, heading);
                } else {
                    objectSubtypeInfo = new RouteCheckerInfo(flags, heading);
                }
            } else {
                objectSubtypeInfo = null;
            }
        } else {
            if ((flags & 0x80) == 0x80) {
                objectSubtypeInfo = new ControlObjectInfo(flags, index, heading);
            } else {
                objectSubtypeInfo = new AutocrossObjectInfo(flags, index, heading);
            }
        }
    }

    /**
     * Creates object information for restricted area.
     * @param x X position (1 metre = 16)
     * @param y Y position (1 metre = 16)
     * @param zByte height (1m = 4)
     * @param restrictedAreaInfo restricted area info
     */
    public ObjectInfo(int x, int y, int zByte, RestrictedAreaInfo restrictedAreaInfo) {
        this.x = (short) x;
        this.y = (short) y;
        this.zByte = (short) zByte;
        objectSubtypeInfo= restrictedAreaInfo;
    }

    /**
     * Creates object information for route checker.
     * @param x X position (1 metre = 16)
     * @param y Y position (1 metre = 16)
     * @param zByte height (1m = 4)
     * @param routeCheckerInfo route checker info
     */
    public ObjectInfo(int x, int y, int zByte, RouteCheckerInfo routeCheckerInfo) {
        this.x = (short) x;
        this.y = (short) y;
        this.zByte = (short) zByte;
        objectSubtypeInfo = routeCheckerInfo;
    }

    /**
     * Creates object information for control object.
     * @param x X position (1 metre = 16)
     * @param y Y position (1 metre = 16)
     * @param zByte height (1m = 4)
     * @param controlObjectInfo control object info
     */
    public ObjectInfo(int x, int y, int zByte, ControlObjectInfo controlObjectInfo) {
        this.x = (short) x;
        this.y = (short) y;
        this.zByte = (short) zByte;
        objectSubtypeInfo = controlObjectInfo;
    }

    /**
     * Creates object information for autocross object.
     * @param x X position (1 metre = 16)
     * @param y Y position (1 metre = 16)
     * @param zByte height (1m = 4)
     * @param autocrossObjectInfo autocross object info
     */
    public ObjectInfo(int x, int y, int zByte, AutocrossObjectInfo autocrossObjectInfo) {
        this.x = (short) x;
        this.y = (short) y;
        this.zByte = (short) zByte;
        objectSubtypeInfo = autocrossObjectInfo;
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
    public Optional<RestrictedAreaInfo> getRestrictedAreaInfo() {
        if (objectSubtypeInfo instanceof RestrictedAreaInfo restrictedAreaInfo) {
            return Optional.of(restrictedAreaInfo);
        } else {
            return Optional.empty();
        }
    }

    /**
     * @return route checker info, or empty if it's not route checker
     */
    public Optional<RouteCheckerInfo> getRouteCheckerInfo() {
        if (objectSubtypeInfo instanceof RouteCheckerInfo routeCheckerInfo) {
            return Optional.of(routeCheckerInfo);
        } else {
            return Optional.empty();
        }
    }

    /**
     * @return whether is an unknown object
     */
    public boolean isUnknownObject() {
        return objectSubtypeInfo == null;
    }

    /**
     * @return control object info, or empty is it's not control object
     */
    public Optional<ControlObjectInfo> getControlObjectInfo() {
        if (objectSubtypeInfo instanceof ControlObjectInfo controlObjectInfo) {
            return Optional.of(controlObjectInfo);
        } else {
            return Optional.empty();
        }
    }

    /**
     * @return autocross object info, or empty if it's not autocross object
     */
    public Optional<AutocrossObjectInfo> getAutocrossObjectInfo() {
        if (objectSubtypeInfo instanceof  AutocrossObjectInfo autocrossObjectInfo) {
            return Optional.of(autocrossObjectInfo);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void appendBytes(PacketBuilder packetBuilder) {
        packetBuilder.writeShort(x)
                .writeShort(y)
                .writeByte(zByte);
        if (objectSubtypeInfo != null) {
            objectSubtypeInfo.appendBytes(packetBuilder);
        } else {
            packetBuilder.writeZeroByte()
                    .writeByte(192)
                    .writeZeroByte();
        }
    }
}
