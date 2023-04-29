package pl.adrian.api.packets.structures.objectinfo;

import pl.adrian.api.packets.enums.ObjectType;
import pl.adrian.api.packets.enums.TyreObjectColour;

/**
 * This class holds information about tyre object.
 */
public class TyreObjectInfo extends AutocrossObjectInfo {
    /**
     * Creates tyre object information. Constructor used only internally.
     * @param x       X position (1 metre = 16)
     * @param y       Y position (1 metre = 16)
     * @param zByte   height (1m = 4)
     * @param flags   object flags
     * @param index   object index
     * @param heading heading
     */
    TyreObjectInfo(short x, short y, short zByte, short flags, ObjectType index, short heading) {
        super(x, y, zByte, flags, index, heading);
    }

    /**
     * @return colour
     */
    public TyreObjectColour getColour() {
        return TyreObjectColour.fromOrdinal(flags & 7);
    }
}
