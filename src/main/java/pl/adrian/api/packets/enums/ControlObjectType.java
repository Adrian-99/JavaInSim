package pl.adrian.api.packets.enums;

import pl.adrian.internal.packets.enums.EnumHelpers;

/**
 * Enumeration for control object type used in {@link pl.adrian.api.packets.structures.ControlObjectInfo ControlObjectInfo}.
 */
public enum ControlObjectType {
    /**
     * value 0, width > 0: finish line
     */
    FINISH_LINE,
    /**
     * value 1: checkpoint 1
     */
    CHECKPOINT_1,
    /**
     * value 2: checkpoint 2
     */
    CHECKPOINT_2,
    /**
     * value 3: checkpoint 3
     */
    CHECKPOINT_3,
    /**
     * value 0, width 0: start position
     */
    START_POSITION;

    /**
     * Converts ordinal number to enum value.
     * @param ordinal ordinal number
     * @return enum value
     */
    public static ControlObjectType fromOrdinal(int ordinal) {
        return EnumHelpers.get(ControlObjectType.class).fromOrdinal(ordinal);
    }
}
