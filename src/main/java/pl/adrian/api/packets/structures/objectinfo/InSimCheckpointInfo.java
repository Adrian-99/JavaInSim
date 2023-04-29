package pl.adrian.api.packets.structures.objectinfo;

import pl.adrian.api.packets.enums.InSimCheckpointType;
import pl.adrian.api.packets.enums.ObjectType;

/**
 * This class holds information about InSim checkpoint.
 */
public class InSimCheckpointInfo extends ObjectInfo {
    /**
     * Creates InSim checkpoint information. Constructor used only internally.
     * @param x       X position (1 metre = 16)
     * @param y       Y position (1 metre = 16)
     * @param zByte   height (1m = 4)
     * @param flags   object flags
     * @param heading heading
     */
    InSimCheckpointInfo(short x, short y, short zByte, short flags, short heading) {
        super(x, y, zByte, flags, ObjectType.MARSH_IS_CP, heading);
    }

    /**
     * @return checkpoint index (seen in the layout editor) - note that the checkpoint index has no meaning in LFS
     * and is provided only for user's convenience. If using many InSim checkpoints it may be needed to identify
     * them with the X and Y values.
     */
    public InSimCheckpointType getCheckpointIndex() {
        return InSimCheckpointType.fromOrdinal(flags & 3);
    }

    /**
     * @return width (2, 4, 6, 8, ..., 62)
     */
    public byte getWidth() {
        return (byte) ((flags >> 1) & 62);
    }

    /**
     * @return heading - 360 degrees in 256 values:<br>
     *  - 128 : heading of zero<br>
     *  - 192 : heading of 90 degrees<br>
     *  - 0   : heading of 180 degrees<br>
     *  - 64  : heading of -90 degrees
     */
    public short getHeading() {
        return heading;
    }
}
