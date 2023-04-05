package pl.adrian.internal.packets.flags;

import pl.adrian.internal.packets.enums.EnumWithCustomValue;

/**
 * This interface should be implemented by all enums representing flags,
 * whose presence in {@link pl.adrian.api.packets.flags.Flags Flags} object
 * should not be dependent only on their value, but on some other custom conditions.
 */
public interface FlagWithCustomBehavior extends EnumWithCustomValue {
    /**
     * Checks if given enum value is present in flags of specified value.
     * @param flagsValue value of flags
     * @return whether enum is present in flags
     */
    boolean isPresent(long flagsValue);
}
