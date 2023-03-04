package pl.adrian.internal.packets.flags;

import pl.adrian.internal.packets.enums.EnumWithCustomValue;

/**
 * This interface must be implemented by all enums representing flags,
 * whose values sent or received from LFS should not be based off their ordinal
 * number but rather some custom value. It is also possible to define value mask,
 * which allows to define which bits should not be set for given flag.
 */
public interface FlagWithCustomValue extends EnumWithCustomValue {
    /**
     * @return mask of enum value
     */
    int getValueMask();
}
