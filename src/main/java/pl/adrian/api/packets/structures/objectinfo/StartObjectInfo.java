package pl.adrian.api.packets.structures.objectinfo;

import pl.adrian.api.packets.enums.ObjectType;

/**
 * This class holds information about start object.
 */
public class StartObjectInfo extends AutocrossObjectInfo {
    /**
     * Creates start object information. Constructor used only internally.
     * @param x X position (1 metre = 16)
     * @param y Y position (1 metre = 16)
     * @param zByte height (1m = 4)
     * @param flags object flags
     * @param index object index
     * @param heading heading
     */
    StartObjectInfo(short x, short y, short zByte, short flags, ObjectType index, short heading) {
        super(x, y, zByte, flags, index, heading);
    }

    /**
     * Creates start object information.
     * @param x X position (1 metre = 16)
     * @param y Y position (1 metre = 16)
     * @param zByte height (1m = 4)
     * @param isFloating whether object is floating
     * @param positionIndex position index
     * @param index object index
     * @param heading heading
     */
    public StartObjectInfo(int x, int y, int zByte, boolean isFloating, int positionIndex, ObjectType index, int heading) {
        super(
                (short) x,
                (short) y,
                (short) zByte,
                calculateFlagsValue(isFloating, positionIndex),
                index,
                (short) heading
        );
    }

    /**
     * @return position index
     */
    public byte getPositionIndex() {
        return (byte) ((flags & 0x3F) + 1);
    }

    private static short calculateFlagsValue(boolean isFloating, int positionIndex) {
        return (short) (
                floatingBitForFlags(isFloating) |
                        normalizeIntValueForFlags(positionIndex - 1, 0, 63, 0)
        );
    }
}
