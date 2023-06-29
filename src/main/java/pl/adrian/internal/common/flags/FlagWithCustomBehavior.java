package pl.adrian.internal.common.flags;

import pl.adrian.api.common.flags.Flags;
import pl.adrian.internal.common.enums.EnumWithCustomValue;

/**
 * This interface should be implemented by all enums representing flags,
 * whose presence in {@link Flags Flags} object
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
