package pl.adrian.api.packets.structures.objectinfo;

import pl.adrian.api.packets.enums.ConcreteObjectColour;
import pl.adrian.api.packets.enums.ObjectType;

/**
 * Abstract class inherited by all classes that represent information about
 * concrete objects.
 */
public abstract class ConcreteObjectInfo extends AutocrossObjectInfo {
    /**
     * Creates concrete object information. Constructor used only internally.
     * @param x       X position (1 metre = 16)
     * @param y       Y position (1 metre = 16)
     * @param zByte   height (1m = 4)
     * @param flags   object flags
     * @param index   object index
     * @param heading heading
     */
    protected ConcreteObjectInfo(short x, short y, short zByte, short flags, ObjectType index, short heading) {
        super(x, y, zByte, flags, index, heading);
    }

    @Override
    public boolean isFloating() {
        return true;
    }

    /**
     * @return width (2, 4, 8, 16)
     */
    protected byte getWidthInternal() {
        return (byte) (2 << (flags & 3));
    }

    /**
     * @return length (2, 4, 8, 16)
     */
    protected byte getLengthInternal() {
        return (byte) (2 << ((flags & 0xC) >> 2));
    }

    /**
     * @return size X (0.25, 0.5, 0.75, 1)
     */
    protected float getSizeXInternal() {
        return ((flags & 3) + 1) / 4.0f;
    }

    /**
     * @return size Y (0.25, 0.5, 0.75, 1)
     */
    protected float getSizeYInternal() {
        return (((flags & 0xC) >> 2) + 1) / 4.0f;
    }

    /**
     * @return height (0.25 to 4 in steps of 0.25)
     */
    protected float getHeightInternal() {
        return (((flags & 0xF0) >> 4) + 1) / 4.0f;
    }

    /**
     * @return pitch (0 to 90 in steps of 6 degrees)
     */
    protected byte getPitchInternal() {
        return (byte) (((flags & 0xF0) >> 4) * 6);
    }

    /**
     * @return colour
     */
    protected ConcreteObjectColour getColourInternal() {
        return ConcreteObjectColour.fromOrdinal(flags & 3);
    }

    /**
     * @return angle (5.625 to 90 in steps of 5.625 deg)
     */
    protected float getAngleInternal() {
        return (((flags & 0xF0) >> 4) + 1) * 5.625f;
    }
}
