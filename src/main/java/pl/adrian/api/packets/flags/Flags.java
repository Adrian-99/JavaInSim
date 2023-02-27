package pl.adrian.api.packets.flags;

import pl.adrian.internal.packets.enums.EnumHelpers;

import java.util.HashSet;
import java.util.Set;

/**
 * This class implements flags type used in communication with LFS.
 * @param <T> Enum that represents bits of flags field
 */
public class Flags<T extends Enum<?>> {
    private final Set<T> flagsSet;

    /**
     * Creates flags out of enum values array.
     * @param flags enum values array
     */
    @SafeVarargs
    public Flags(T... flags) {
        this.flagsSet = Set.of(flags);
    }

    /**
     * Creates flags out of their binary representation.
     * @param enumClass class of the enum that holds possible values for these flags
     * @param wordValue binary representation of flags
     */
    public Flags(Class<T> enumClass, int wordValue) {
        flagsSet = new HashSet<>();
        for (var enumValue : EnumHelpers.get(enumClass).getAllValuesCached()) {
            var flagValue = 1 << enumValue.ordinal();
            if ((wordValue & flagValue) == flagValue) {
                flagsSet.add(enumValue);
            }
        }
    }

    /**
     * @return integer value of flags
     */
    public int getValue() {
        return flagsSet.stream().mapToInt(e -> 1 << e.ordinal()).sum();
    }

    /**
     * Checks if specified value is present in flags.
     * @param flag value to check
     * @return whether value is present in flags
     */
    public boolean hasFlag(T flag) {
        return flagsSet.contains(flag);
    }

    /**
     * Checks if specified value is not present in flags.
     * @param flag value to check
     * @return whether value is not present in flags
     */
    public boolean hasNoFlag(T flag) {
        return !flagsSet.contains(flag);
    }
}
