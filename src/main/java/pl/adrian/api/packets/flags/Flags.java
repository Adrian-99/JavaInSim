package pl.adrian.api.packets.flags;

import java.util.Set;

public class Flags<T extends Enum<?>> {
    private final Set<T> flagsSet;

    @SafeVarargs
    public Flags(T... flags) {
        this.flagsSet = Set.of(flags);
    }

    public int getValue() {
        return flagsSet.stream().mapToInt(e -> 1 << e.ordinal()).sum();
    }
}
