/*
 * Copyright (c) 2023, Adrian-99
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package pl.adrian.api.insim.packets.structures.objectinfo;

import pl.adrian.api.insim.packets.enums.ObjectType;
import pl.adrian.internal.insim.packets.annotations.Byte;
import pl.adrian.internal.insim.packets.annotations.Short;
import pl.adrian.internal.insim.packets.structures.base.ComplexInstructionStructure;
import pl.adrian.internal.insim.packets.util.PacketBuilder;
import pl.adrian.internal.common.util.PacketDataBytes;

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
     * approximate altitude with a value from 0 to 240 (1m = 4)
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
    protected final ObjectType index;

    /**
     * heading - 360 degrees in 256 values:<br>
     *  - 128 : heading of zero<br>
     *  - 192 : heading of 90 degrees<br>
     *  - 0   : heading of 180 degrees<br>
     *  - 64  : heading of -90 degrees
     */
    @Byte
    protected final short heading;

    /**
     * Creates object information. Constructor used only internally.
     * @param x X position (1 metre = 16)
     * @param y Y position (1 metre = 16)
     * @param zByte height (1m = 4)
     * @param flags object flags
     * @param index object index
     * @param heading heading
     */
    protected ObjectInfo(short x, short y, short zByte, short flags, ObjectType index, short heading) {
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
     * @return approximate altitude with a value from 0 to 240 (1m = 4)
     */
    public short getZByte() {
        return zByte;
    }

    /**
     * @return whether object if floating - correct value for all objects except concrete objects
     */
    public boolean isFloating() {
        return (flags & 0x80) == 0x80;
    }

    /**
     * @return object index
     */
    public ObjectType getIndex() {
        return index;
    }

    @Override
    public void appendBytes(PacketBuilder packetBuilder) {
        packetBuilder.writeShort(x)
                .writeShort(y)
                .writeByte(zByte)
                .writeByte(flags)
                .writeByte(index.getValue())
                .writeByte(heading);
    }

    /**
     * Reads appropriate object info implementation from packet data bytes.
     * @param packetDataBytes packet data bytes
     * @return read object info
     */
    public static ObjectInfo read(PacketDataBytes packetDataBytes) {
        return read(packetDataBytes, false);
    }

    /**
     * Reads appropriate object info implementation from packet data bytes.
     * @param packetDataBytes packet data bytes
     * @param asUnknownObjectInfo whether {@link UnknownObjectInfo} should be returned regardless of index value
     * @return read object info
     */
    @SuppressWarnings("java:S3776")
    public static ObjectInfo read(PacketDataBytes packetDataBytes, boolean asUnknownObjectInfo) {
        var x = packetDataBytes.readShort();
        var y = packetDataBytes.readShort();
        var zByte = packetDataBytes.readByte();
        var flags = packetDataBytes.readByte();
        var index = packetDataBytes.readByte();
        var heading = packetDataBytes.readByte();

        if (asUnknownObjectInfo) {
            return new UnknownObjectInfo(x, y, zByte, flags, heading);
        } else if (index == 0) {
            return new SpecialControlObjectInfo(x, y, zByte, flags, heading);
        } else if (index >= 4 && index < 192) {
            var objectType = ObjectType.fromOrdinal(index);
            if (index < 13) {
                return new ChalkLineInfo(x, y, zByte, flags, objectType, heading);
            } else if (index >= 48 && index <= 55) {
                return new TyreObjectInfo(x, y, zByte, flags, objectType, heading);
            } else if (index == 149) {
                return new StartLightsInfo(x, y, zByte, flags, heading);
            } else if (index >= 172 && index <= 179) {
                return switch (index) {
                    case 173 -> new ConcreteRampInfo(x, y, zByte, flags, heading);
                    case 174 -> new ConcreteWallInfo(x, y, zByte, flags, heading);
                    case 175 -> new ConcretePillarInfo(x, y, zByte, flags, heading);
                    case 176 -> new ConcreteSlabWallInfo(x, y, zByte, flags, heading);
                    case 177 -> new ConcreteRampWallInfo(x, y, zByte, flags, heading);
                    case 178 -> new ConcreteShortSlabWallInfo(x, y, zByte, flags, heading);
                    case 179 -> new ConcreteWedgeInfo(x, y, zByte, flags, heading);
                    default -> new ConcreteSlabInfo(x, y, zByte, flags, heading);
                };
            } else if (index >= 184 && index <= 185) {
                return new StartObjectInfo(x, y, zByte, flags, objectType, heading);
            } else {
                return new AutocrossObjectInfo(x, y, zByte, flags, objectType, heading);
            }
        } else if (index == 252) {
            return new InSimCheckpointInfo(x, y, zByte, flags, heading);
        } else if (index == 253) {
            return new InSimCircleInfo(x, y, zByte, flags, heading);
        } else if (index == 254) {
            return new RestrictedAreaInfo(x, y, zByte, flags, heading);
        } else if (index == 255) {
            return new RouteCheckerInfo(x, y, zByte, flags, heading);
        } else {
            return new UnknownObjectInfo(x, y, zByte, flags, heading);
        }
    }

    /**
     * Converts floating flag to flags value.
     * @param isFloating whether object is floating
     * @return flags value with floating bit
     */
    protected static short floatingBitForFlags(boolean isFloating) {
        return (short) (isFloating ? 0x80 : 0);
    }

    /**
     * Converts int value to specified range.
     * @param value int value
     * @param min minimum value
     * @param max maximum value
     * @param shift number of shifting left bits after conversion
     * @return result value
     */
    protected static int normalizeIntValueForFlags(int value, int min, int max, int shift) {
        return normalizeIntValueForFlags(value, min, max, max, shift);
    }

    /**
     * Converts int value to specified range.
     * @param value int value
     * @param min minimum value
     * @param max maximum value
     * @param mask value mask
     * @param shift number of shifting left bits after conversion
     * @return result value
     */
    protected static int normalizeIntValueForFlags(int value, int min, int max, int mask, int shift) {
        return (Math.min(max, Math.max(min, value)) & mask) << shift;
    }
}
