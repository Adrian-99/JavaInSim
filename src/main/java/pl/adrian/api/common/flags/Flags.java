package pl.adrian.api.common.flags;

import pl.adrian.internal.common.enums.EnumHelpers;
import pl.adrian.internal.common.enums.EnumWithCustomValue;
import pl.adrian.internal.common.flags.FlagWithCustomBehavior;
import pl.adrian.internal.common.flags.FlagWithCustomValue;
import pl.adrian.internal.insim.packets.structures.base.ByteInstructionStructure;
import pl.adrian.internal.insim.packets.structures.base.UnsignedInstructionStructure;
import pl.adrian.internal.insim.packets.structures.base.WordInstructionStructure;

import java.util.HashSet;
import java.util.Set;

/**
 * This class implements flags type used in communication with LFS.
 * @param <T> Enum that represents bits of flags field
 */
public class Flags<T extends Enum<?>> implements ByteInstructionStructure,
        WordInstructionStructure, UnsignedInstructionStructure {
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
     * @param value binary representation of flags
     */
    public Flags(Class<T> enumClass, long value) {
        flagsSet = new HashSet<>();
        for (var enumValue : EnumHelpers.get(enumClass).getAllValuesCached()) {
            if (enumValue instanceof FlagWithCustomValue enumCustomValue) {
                var flagValue = enumCustomValue.getValue();
                var requiredZeros = enumCustomValue.getValueMask() - flagValue;
                if ((value & flagValue) == flagValue && (value & requiredZeros) == 0) {
                    flagsSet.add(enumValue);
                }
            } else if (enumValue instanceof FlagWithCustomBehavior enumCustomBehavior) {
                if (enumCustomBehavior.isPresent(value)) {
                    flagsSet.add(enumValue);
                }
            } else {
                var flagValue = 1 << enumValue.ordinal();
                if ((value & flagValue) == flagValue) {
                    flagsSet.add(enumValue);
                }
            }
        }
    }

    @Override
    public short getByteValue() {
        return (short) getUnsignedValue();
    }

    @Override
    public int getWordValue() {
        return (int) getUnsignedValue();
    }

    @Override
    public long getUnsignedValue() {
        return flagsSet.stream().mapToLong(enumValue -> {
            if (enumValue instanceof EnumWithCustomValue enumCustomValue) {
                return enumCustomValue.getValue();
            } else {
                return (long) 1 << enumValue.ordinal();
            }
        }).reduce(0, (all, curr) -> all | curr);
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
