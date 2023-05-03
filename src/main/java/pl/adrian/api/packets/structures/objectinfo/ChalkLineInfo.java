package pl.adrian.api.packets.structures.objectinfo;

import pl.adrian.api.packets.enums.ChalkLineColour;
import pl.adrian.api.packets.enums.ObjectType;

/**
 * This class holds information about chalk line object.
 */
public class ChalkLineInfo extends AutocrossObjectInfo {
    /**
     * Creates chalk line object information. Constructor used only internally.
     * @param x X position (1 metre = 16)
     * @param y Y position (1 metre = 16)
     * @param zByte height (1m = 4)
     * @param flags object flags
     * @param index object index
     * @param heading heading
     */
    ChalkLineInfo(short x, short y, short zByte, short flags, ObjectType index, short heading) {
        super(x, y, zByte, flags, index, heading);
    }

    /**
     * Creates chalk line object information.
     * @param x X position (1 metre = 16)
     * @param y Y position (1 metre = 16)
     * @param zByte height (1m = 4)
     * @param isFloating whether object is floating
     * @param colour colour
     * @param index object index
     * @param heading heading
     */
    public ChalkLineInfo(int x,
                         int y,
                         int zByte,
                         boolean isFloating,
                         ChalkLineColour colour,
                         ObjectType index,
                         int heading) {
        super(
                (short) x,
                (short) y,
                (short) zByte,
                (short) (floatingBitForFlags(isFloating) | colour.ordinal()),
                index,
                (short) heading
        );
    }

    /**
     * @return colour
     */
    public ChalkLineColour getColour() {
        return ChalkLineColour.fromOrdinal(flags & 3);
    }
}
