package pl.adrian.api.packets.flags;

import java.util.Set;

/**
 * This class implements flags type used in communication with LFS
 * @param <T> Enum that represents bits of flags field
 */
public class Flags<T extends Enum<?>> {
    private final Set<T> flagsSet;

    /**
     * Creates flags out of enum values array
     * @param flags Enum values array
     */
    @SafeVarargs
    public Flags(T... flags) {
        this.flagsSet = Set.of(flags);
    }

    /**
     * @return Integer value of flags
     */
    public int getValue() {
        return flagsSet.stream().mapToInt(e -> 1 << e.ordinal()).sum();
    }
}
